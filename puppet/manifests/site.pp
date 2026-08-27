# puppet/manifests/site.pp
# Puppet bootstrap: Java, Docker, system users, OS hardening

# ── Java 21 ───────────────────────────────────────────────────────────────────
class java_install {
  exec { 'apt-update':
    command => '/usr/bin/apt-get update',
    unless  => '/usr/bin/find /var/cache/apt/pkgcache.bin -mmin -60 | /usr/bin/grep -q .',
  }

  package { 'openjdk-21-jdk':
    ensure  => installed,
    require => Exec['apt-update'],
  }
}

# ── Docker ────────────────────────────────────────────────────────────────────
class docker_install {
  package { 'docker.io':
    ensure  => installed,
    require => Exec['apt-update'],
  }

  service { 'docker':
    ensure  => running,
    enable  => true,
    require => Package['docker.io'],
  }
}

# ── System Users ──────────────────────────────────────────────────────────────
class system_users {
  group { 'appgroup':
    ensure => present,
  }

  user { 'appuser':
    ensure     => present,
    gid        => 'appgroup',
    managehome => true,
    shell      => '/bin/bash',
    groups     => ['docker'],
    require    => [Group['appgroup'], Service['docker']],
  }
}

# ── OS Hardening ──────────────────────────────────────────────────────────────
class os_hardening {
  file { '/etc/ssh/sshd_config.d/99-hardening.conf':
    ensure  => present,
    owner   => 'root',
    group   => 'root',
    mode    => '0644',
    content => "PermitRootLogin no\nPasswordAuthentication no\nMaxAuthTries 3\nClientAliveInterval 300\nClientAliveCountMax 2\nX11Forwarding no\n",
    notify  => Service['ssh'],
  }

  service { 'ssh':
    ensure => running,
    enable => true,
  }

  file { '/etc/sysctl.d/99-hardening.conf':
    ensure  => present,
    owner   => 'root',
    group   => 'root',
    mode    => '0644',
    content => "net.ipv4.conf.all.accept_redirects = 0\nnet.ipv4.conf.all.send_redirects = 0\nnet.ipv4.tcp_syncookies = 1\n",
    notify  => Exec['sysctl-reload'],
  }

  exec { 'sysctl-reload':
    command     => '/sbin/sysctl --system',
    refreshonly => true,
  }
}

# ── Apply all classes ─────────────────────────────────────────────────────────
include java_install
include docker_install
include system_users
include os_hardening
