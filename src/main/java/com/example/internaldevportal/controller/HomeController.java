package com.example.internaldevportal.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return landingPage();
    }

    @GetMapping(value = "/app", produces = MediaType.TEXT_HTML_VALUE)
    public String app() {
        return appPage();
    }

    // ─────────────────────────────────────────────
    // Landing page (public) — clean marketing page
    // ─────────────────────────────────────────────
    private String landingPage() {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
  <title>Internal Developer Portal</title>
  <style>
    *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
    body{font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;background:#0a0c10;color:#e2e8f0;min-height:100vh;display:flex;flex-direction:column}
    header{padding:1.25rem 2.5rem;border-bottom:1px solid #161b26;display:flex;align-items:center;justify-content:space-between}
    .logo{display:flex;align-items:center;gap:.75rem}
    .logo-mark{width:34px;height:34px;background:linear-gradient(135deg,#6366f1,#8b5cf6);border-radius:8px;display:flex;align-items:center;justify-content:center;font-weight:700;font-size:.95rem;color:#fff}
    .logo-text{font-size:1rem;font-weight:600;color:#f1f5f9}
    .nav-actions{display:flex;gap:.75rem}
    .btn{display:inline-flex;align-items:center;gap:.4rem;padding:.55rem 1.2rem;border-radius:8px;font-size:.88rem;font-weight:500;text-decoration:none;transition:all .15s;cursor:pointer;border:none}
    .btn-ghost{background:transparent;color:#94a3b8;border:1px solid #1e2535}
    .btn-ghost:hover{background:#161b26;color:#f1f5f9}
    .btn-primary{background:linear-gradient(135deg,#6366f1,#8b5cf6);color:#fff}
    .btn-primary:hover{opacity:.88;transform:translateY(-1px)}
    main{flex:1;display:flex;flex-direction:column;align-items:center;justify-content:center;padding:5rem 1.5rem;text-align:center}
    .pill{display:inline-flex;align-items:center;gap:.4rem;background:#161b26;border:1px solid #1e2535;border-radius:100px;padding:.3rem .9rem;font-size:.76rem;color:#94a3b8;margin-bottom:2rem}
    .pill .dot{width:6px;height:6px;border-radius:50%;background:#22c55e;animation:pulse 2s infinite}
    @keyframes pulse{0%,100%{opacity:1}50%{opacity:.4}}
    h1{font-size:clamp(2.2rem,6vw,3.5rem);font-weight:700;line-height:1.15;margin-bottom:1.2rem;background:linear-gradient(135deg,#f1f5f9 30%,#94a3b8);-webkit-background-clip:text;-webkit-text-fill-color:transparent;background-clip:text}
    .sub{font-size:1.05rem;color:#64748b;max-width:500px;line-height:1.75;margin-bottom:2.5rem}
    .cta{display:flex;gap:.75rem;flex-wrap:wrap;justify-content:center;margin-bottom:5rem}
    .features{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:1rem;max-width:960px;width:100%}
    .feat{background:#0e1117;border:1px solid #161b26;border-radius:14px;padding:1.75rem;text-align:left;transition:border-color .2s}
    .feat:hover{border-color:#6366f1}
    .feat-icon{font-size:1.5rem;margin-bottom:.9rem}
    .feat h3{font-size:.95rem;font-weight:600;color:#f1f5f9;margin-bottom:.4rem}
    .feat p{font-size:.82rem;color:#64748b;line-height:1.6}
    footer{text-align:center;padding:1.25rem;font-size:.75rem;color:#334155;border-top:1px solid #161b26}
    footer a{color:#6366f1;text-decoration:none}
  </style>
</head>
<body>
<header>
  <div class="logo">
    <div class="logo-mark">IDP</div>
    <span class="logo-text">Internal Developer Portal</span>
  </div>
  <nav class="nav-actions">
    <a href="/app#login" class="btn btn-ghost">Sign in</a>
    <a href="/app#register" class="btn btn-primary">Get started</a>
  </nav>
</header>
<main>
  <div class="pill"><span class="dot"></span> All systems operational</div>
  <h1>Your Engineering<br/>Command Centre</h1>
  <p class="sub">One place to manage every project, team, environment and deployment across your engineering organisation.</p>
  <div class="cta">
    <a href="/app#register" class="btn btn-primary" style="padding:.75rem 2rem;font-size:1rem">Get started free</a>
    <a href="/app#login" class="btn btn-ghost" style="padding:.75rem 1.5rem;font-size:1rem">Sign in</a>
  </div>
  <div class="features">
    <div class="feat"><div class="feat-icon">📁</div><h3>Projects</h3><p>Track engineering projects with owners, status, and team assignments.</p></div>
    <div class="feat"><div class="feat-icon">👥</div><h3>Teams</h3><p>Organise engineers into teams and manage cross-project ownership.</p></div>
    <div class="feat"><div class="feat-icon">🌍</div><h3>Environments</h3><p>Model dev, staging and production per project with full auditability.</p></div>
    <div class="feat"><div class="feat-icon">🚀</div><h3>Deployments</h3><p>Record every deployment with status, timestamps and a full history.</p></div>
  </div>
</main>
<footer>Internal Developer Portal &mdash; <a href="/swagger-ui/index.html">API Docs</a> &middot; <a href="/actuator/health">Health</a></footer>
</body>
</html>
""";
    }

    // ─────────────────────────────────────────────────────────────────────────
    // App page — full SPA: Login / Register / Dashboard (no page reloads)
    // ─────────────────────────────────────────────────────────────────────────
    private String appPage() {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
  <title>IDP — App</title>
  <style>
    /* ── reset & base ── */
    *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
    :root{
      --bg:#0a0c10;--surface:#0e1117;--surface2:#141824;
      --border:#1e2535;--border2:#263145;
      --text:#e2e8f0;--text2:#94a3b8;--text3:#64748b;
      --accent:#6366f1;--accent2:#8b5cf6;
      --success:#22c55e;--danger:#ef4444;--warn:#f59e0b;
    }
    html,body{height:100%}
    body{font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;background:var(--bg);color:var(--text);min-height:100vh;display:flex;flex-direction:column}
    a{color:var(--accent);text-decoration:none}
    a:hover{text-decoration:underline}
    /* ── auth views ── */
    .auth-wrap{flex:1;display:flex;align-items:center;justify-content:center;padding:2rem 1rem}
    .auth-card{background:var(--surface);border:1px solid var(--border);border-radius:16px;padding:2.5rem;width:100%;max-width:400px}
    .auth-logo{display:flex;align-items:center;gap:.6rem;margin-bottom:2rem}
    .logo-mark{width:32px;height:32px;background:linear-gradient(135deg,var(--accent),var(--accent2));border-radius:8px;display:flex;align-items:center;justify-content:center;font-weight:700;font-size:.85rem;color:#fff}
    .auth-title{font-size:1.35rem;font-weight:700;color:var(--text);margin-bottom:.35rem}
    .auth-sub{font-size:.85rem;color:var(--text3);margin-bottom:1.75rem}
    .form-group{margin-bottom:1.1rem}
    label{display:block;font-size:.82rem;font-weight:500;color:var(--text2);margin-bottom:.4rem}
    input[type=text],input[type=password],input[type=email]{width:100%;background:var(--surface2);border:1px solid var(--border);border-radius:8px;padding:.65rem .9rem;font-size:.9rem;color:var(--text);outline:none;transition:border-color .15s}
    input:focus{border-color:var(--accent)}
    .btn-full{width:100%;padding:.75rem;border-radius:8px;font-size:.95rem;font-weight:600;cursor:pointer;border:none;transition:all .15s;margin-top:.5rem}
    .btn-accent{background:linear-gradient(135deg,var(--accent),var(--accent2));color:#fff}
    .btn-accent:hover{opacity:.88}
    .btn-accent:disabled{opacity:.5;cursor:not-allowed}
    .form-footer{text-align:center;font-size:.83rem;color:var(--text3);margin-top:1.25rem}
    .form-footer a{color:var(--accent);cursor:pointer}
    .err-msg{background:#1f1115;border:1px solid #5b2130;color:#fca5a5;border-radius:8px;padding:.65rem .9rem;font-size:.83rem;margin-bottom:1rem;display:none}
    .ok-msg{background:#0f1f14;border:1px solid #166534;color:#86efac;border-radius:8px;padding:.65rem .9rem;font-size:.83rem;margin-bottom:1rem;display:none}
    /* ── app shell ── */
    #shell{display:flex;height:100vh;overflow:hidden}
    /* sidebar */
    .sidebar{width:230px;min-width:230px;background:var(--surface);border-right:1px solid var(--border);display:flex;flex-direction:column;padding:1.25rem 0}
    .sidebar-logo{display:flex;align-items:center;gap:.6rem;padding:.25rem 1.25rem 1.5rem}
    .sidebar-logo .logo-mark{width:30px;height:30px;font-size:.8rem}
    .sidebar-logo span{font-size:.95rem;font-weight:600;color:var(--text)}
    .nav-section{padding:0 .75rem;margin-bottom:.5rem}
    .nav-label{font-size:.68rem;font-weight:600;color:var(--text3);text-transform:uppercase;letter-spacing:.08em;padding:.25rem .5rem;margin-bottom:.25rem}
    .nav-item{display:flex;align-items:center;gap:.7rem;padding:.55rem .75rem;border-radius:8px;font-size:.88rem;color:var(--text2);cursor:pointer;transition:all .15s;border:none;background:none;width:100%;text-align:left}
    .nav-item:hover{background:var(--surface2);color:var(--text)}
    .nav-item.active{background:linear-gradient(135deg,rgba(99,102,241,.15),rgba(139,92,246,.1));color:var(--accent);border:1px solid rgba(99,102,241,.2)}
    .nav-icon{font-size:1rem;width:1.2rem;text-align:center}
    .sidebar-footer{margin-top:auto;padding:.75rem 1.25rem;border-top:1px solid var(--border)}
    .user-chip{display:flex;align-items:center;gap:.65rem}
    .avatar{width:30px;height:30px;border-radius:50%;background:linear-gradient(135deg,var(--accent),var(--accent2));display:flex;align-items:center;justify-content:center;font-size:.8rem;font-weight:700;color:#fff;flex-shrink:0}
    .user-info{flex:1;min-width:0}
    .user-name{font-size:.83rem;font-weight:600;color:var(--text);white-space:nowrap;overflow:hidden;text-overflow:ellipsis}
    .user-role{font-size:.72rem;color:var(--text3)}
    .btn-icon{background:none;border:none;cursor:pointer;color:var(--text3);padding:.25rem;border-radius:4px;transition:color .15s}
    .btn-icon:hover{color:var(--danger)}
    /* main content */
    .main-area{flex:1;overflow-y:auto;display:flex;flex-direction:column}
    .topbar{padding:1rem 2rem;border-bottom:1px solid var(--border);display:flex;align-items:center;justify-content:space-between;background:var(--surface);position:sticky;top:0;z-index:10}
    .topbar-title{font-size:1.1rem;font-weight:700;color:var(--text)}
    .topbar-actions{display:flex;gap:.6rem}
    .btn-sm{display:inline-flex;align-items:center;gap:.4rem;padding:.45rem 1rem;border-radius:7px;font-size:.83rem;font-weight:500;cursor:pointer;border:none;transition:all .15s}
    .btn-sm-primary{background:linear-gradient(135deg,var(--accent),var(--accent2));color:#fff}
    .btn-sm-primary:hover{opacity:.88}
    .btn-sm-ghost{background:var(--surface2);color:var(--text2);border:1px solid var(--border)}
    .btn-sm-ghost:hover{background:var(--border2);color:var(--text)}
    .content{padding:1.75rem 2rem;flex:1}
    /* stats bar */
    .stats{display:grid;grid-template-columns:repeat(auto-fit,minmax(160px,1fr));gap:1rem;margin-bottom:1.75rem}
    .stat-card{background:var(--surface);border:1px solid var(--border);border-radius:12px;padding:1.25rem 1.5rem}
    .stat-label{font-size:.75rem;color:var(--text3);font-weight:500;text-transform:uppercase;letter-spacing:.05em;margin-bottom:.4rem}
    .stat-value{font-size:1.75rem;font-weight:700;color:var(--text)}
    .stat-sub{font-size:.75rem;color:var(--text3);margin-top:.25rem}
    /* table */
    .table-wrap{background:var(--surface);border:1px solid var(--border);border-radius:12px;overflow:hidden}
    .table-header{padding:1rem 1.5rem;border-bottom:1px solid var(--border);display:flex;align-items:center;justify-content:space-between}
    .table-header h3{font-size:.95rem;font-weight:600;color:var(--text)}
    .search-box{display:flex;align-items:center;gap:.5rem;background:var(--surface2);border:1px solid var(--border);border-radius:7px;padding:.4rem .75rem}
    .search-box input{background:none;border:none;outline:none;color:var(--text);font-size:.83rem;width:160px}
    table{width:100%;border-collapse:collapse}
    th{padding:.75rem 1.25rem;text-align:left;font-size:.75rem;font-weight:600;color:var(--text3);text-transform:uppercase;letter-spacing:.05em;border-bottom:1px solid var(--border);background:var(--surface2)}
    td{padding:.9rem 1.25rem;font-size:.86rem;color:var(--text2);border-bottom:1px solid var(--border)}
    tr:last-child td{border-bottom:none}
    tr:hover td{background:var(--surface2)}
    td .name{font-weight:500;color:var(--text)}
    .badge{display:inline-flex;align-items:center;padding:.2rem .65rem;border-radius:100px;font-size:.74rem;font-weight:500}
    .badge-green{background:#0f2e1a;color:#4ade80;border:1px solid #166534}
    .badge-blue{background:#0d1f3c;color:#60a5fa;border:1px solid #1d4ed8}
    .badge-yellow{background:#1f1a07;color:#fbbf24;border:1px solid #92400e}
    .badge-red{background:#1f0a0a;color:#f87171;border:1px solid #7f1d1d}
    .badge-gray{background:#1a1f2e;color:#94a3b8;border:1px solid var(--border)}
    .row-actions{display:flex;gap:.4rem}
    .btn-row{padding:.3rem .65rem;border-radius:6px;font-size:.76rem;cursor:pointer;border:1px solid var(--border);background:var(--surface2);color:var(--text2);transition:all .15s}
    .btn-row:hover{background:var(--border2);color:var(--text)}
    .btn-row-danger:hover{border-color:var(--danger);color:var(--danger);background:#1f0a0a}
    .empty-state{text-align:center;padding:3.5rem 1rem;color:var(--text3)}
    .empty-icon{font-size:2.5rem;margin-bottom:.75rem}
    .empty-state p{font-size:.88rem}
    /* modal */
    .modal-backdrop{position:fixed;inset:0;background:rgba(0,0,0,.6);display:flex;align-items:center;justify-content:center;z-index:1000;padding:1rem}
    .modal{background:var(--surface);border:1px solid var(--border2);border-radius:16px;padding:2rem;width:100%;max-width:440px;max-height:90vh;overflow-y:auto}
    .modal-title{font-size:1.05rem;font-weight:700;color:var(--text);margin-bottom:1.5rem}
    .modal-actions{display:flex;gap:.6rem;justify-content:flex-end;margin-top:1.5rem}
    .btn-cancel{background:var(--surface2);color:var(--text2);border:1px solid var(--border);padding:.55rem 1.1rem;border-radius:7px;font-size:.88rem;cursor:pointer}
    .btn-submit{background:linear-gradient(135deg,var(--accent),var(--accent2));color:#fff;border:none;padding:.55rem 1.25rem;border-radius:7px;font-size:.88rem;cursor:pointer;font-weight:600}
    .btn-submit:hover{opacity:.88}
    select{width:100%;background:var(--surface2);border:1px solid var(--border);border-radius:8px;padding:.65rem .9rem;font-size:.9rem;color:var(--text);outline:none}
    select:focus{border-color:var(--accent)}
    /* loading */
    .spinner{display:inline-block;width:18px;height:18px;border:2px solid var(--border);border-top-color:var(--accent);border-radius:50%;animation:spin .7s linear infinite}
    @keyframes spin{to{transform:rotate(360deg)}}
    .loading-row td{text-align:center;padding:2rem}
    /* toast */
    #toast{position:fixed;bottom:1.5rem;right:1.5rem;z-index:2000;display:flex;flex-direction:column;gap:.5rem}
    .toast{background:var(--surface);border:1px solid var(--border2);border-radius:10px;padding:.75rem 1.1rem;font-size:.85rem;color:var(--text);box-shadow:0 8px 24px rgba(0,0,0,.5);animation:slideIn .2s ease;display:flex;align-items:center;gap:.6rem}
    .toast.toast-ok{border-left:3px solid var(--success)}
    .toast.toast-err{border-left:3px solid var(--danger)}
    @keyframes slideIn{from{opacity:0;transform:translateX(20px)}to{opacity:1;transform:translateX(0)}}
    /* misc */
    .hidden{display:none!important}
    #auth-wrap,#shell{display:none}
    .tab-bar{display:flex;gap:.25rem;margin-bottom:1.5rem;background:var(--surface2);border-radius:10px;padding:.3rem;width:fit-content}
    .tab{padding:.45rem 1.1rem;border-radius:7px;font-size:.85rem;font-weight:500;cursor:pointer;color:var(--text3);border:none;background:none;transition:all .15s}
    .tab.active{background:var(--surface);color:var(--text);box-shadow:0 1px 4px rgba(0,0,0,.3)}
  </style>
</head>
<body>

<!-- ══════════════════════════════════
     AUTH VIEWS  (login / register)
══════════════════════════════════ -->
<div id="auth-wrap" class="auth-wrap">
  <!-- LOGIN -->
  <div id="view-login" class="auth-card">
    <div class="auth-logo">
      <div class="logo-mark">IDP</div>
      <span style="font-size:.95rem;font-weight:600;color:var(--text)">Internal Developer Portal</span>
    </div>
    <div class="auth-title">Welcome back</div>
    <div class="auth-sub">Sign in to your account to continue</div>
    <div class="err-msg" id="login-err"></div>
    <div class="form-group">
      <label>Username</label>
      <input type="text" id="login-user" placeholder="your username" autocomplete="username"/>
    </div>
    <div class="form-group">
      <label>Password</label>
      <input type="password" id="login-pass" placeholder="••••••••" autocomplete="current-password"/>
    </div>
    <button class="btn-full btn-accent" id="login-btn" onclick="doLogin()">Sign in</button>
    <div class="form-footer">Don't have an account? <a onclick="showView('register')">Create one</a></div>
  </div>

  <!-- REGISTER -->
  <div id="view-register" class="auth-card hidden">
    <div class="auth-logo">
      <div class="logo-mark">IDP</div>
      <span style="font-size:.95rem;font-weight:600;color:var(--text)">Internal Developer Portal</span>
    </div>
    <div class="auth-title">Create account</div>
    <div class="auth-sub">Get access to the developer portal</div>
    <div class="err-msg" id="reg-err"></div>
    <div class="ok-msg" id="reg-ok"></div>
    <div class="form-group">
      <label>Username</label>
      <input type="text" id="reg-user" placeholder="choose a username" autocomplete="username"/>
    </div>
    <div class="form-group">
      <label>Email</label>
      <input type="email" id="reg-email" placeholder="you@company.com" autocomplete="email"/>
    </div>
    <div class="form-group">
      <label>Password</label>
      <input type="password" id="reg-pass" placeholder="min 8 characters" autocomplete="new-password"/>
    </div>
    <button class="btn-full btn-accent" id="reg-btn" onclick="doRegister()">Create account</button>
    <div class="form-footer">Already have an account? <a onclick="showView('login')">Sign in</a></div>
  </div>
</div>

<!-- ══════════════════════════════════
     APP SHELL  (authenticated)
══════════════════════════════════ -->
<div id="shell">
  <!-- SIDEBAR -->
  <aside class="sidebar">
    <div class="sidebar-logo">
      <div class="logo-mark">IDP</div>
      <span>Developer Portal</span>
    </div>
    <div class="nav-section">
      <div class="nav-label">Workspace</div>
      <button class="nav-item active" id="nav-projects" onclick="navigate('projects')"><span class="nav-icon">📁</span> Projects</button>
      <button class="nav-item" id="nav-teams" onclick="navigate('teams')"><span class="nav-icon">👥</span> Teams</button>
      <button class="nav-item" id="nav-environments" onclick="navigate('environments')"><span class="nav-icon">🌍</span> Environments</button>
      <button class="nav-item" id="nav-deployments" onclick="navigate('deployments')"><span class="nav-icon">🚀</span> Deployments</button>
    </div>
    <div class="nav-section" style="margin-top:.5rem">
      <div class="nav-label">System</div>
      <button class="nav-item" onclick="window.open('/actuator/health','_blank')"><span class="nav-icon">❤️</span> Health</button>
      <button class="nav-item" onclick="window.open('/swagger-ui/index.html','_blank')"><span class="nav-icon">📄</span> API Docs</button>
    </div>
    <div class="sidebar-footer">
      <div class="user-chip">
        <div class="avatar" id="user-avatar">?</div>
        <div class="user-info">
          <div class="user-name" id="user-name-display">—</div>
          <div class="user-role" id="user-role-display">—</div>
        </div>
        <button class="btn-icon" onclick="logout()" title="Sign out">⏻</button>
      </div>
    </div>
  </aside>

  <!-- MAIN -->
  <div class="main-area">
    <!-- TOPBAR -->
    <div class="topbar">
      <div class="topbar-title" id="page-title">Projects</div>
      <div class="topbar-actions">
        <button class="btn-sm btn-sm-primary" id="create-btn" onclick="openCreate()">+ New</button>
      </div>
    </div>

    <!-- CONTENT -->
    <div class="content">
      <!-- PROJECTS -->
      <div id="page-projects">
        <div class="stats" id="proj-stats"></div>
        <div class="table-wrap">
          <div class="table-header">
            <h3>All Projects</h3>
            <div class="search-box">🔍 <input type="text" id="proj-search" placeholder="Search…" oninput="filterTable('projects')"/></div>
          </div>
          <table><thead><tr>
            <th>Name</th><th>Description</th><th>Status</th><th>Actions</th>
          </tr></thead>
          <tbody id="proj-body"></tbody></table>
        </div>
      </div>

      <!-- TEAMS -->
      <div id="page-teams" class="hidden">
        <div class="table-wrap">
          <div class="table-header">
            <h3>All Teams</h3>
            <div class="search-box">🔍 <input type="text" id="team-search" placeholder="Search…" oninput="filterTable('teams')"/></div>
          </div>
          <table><thead><tr>
            <th>Name</th><th>Description</th><th>Actions</th>
          </tr></thead>
          <tbody id="team-body"></tbody></table>
        </div>
      </div>

      <!-- ENVIRONMENTS -->
      <div id="page-environments" class="hidden">
        <div class="table-wrap">
          <div class="table-header">
            <h3>All Environments</h3>
            <div class="search-box">🔍 <input type="text" id="env-search" placeholder="Search…" oninput="filterTable('environments')"/></div>
          </div>
          <table><thead><tr>
            <th>Name</th><th>Type</th><th>Project</th><th>Actions</th>
          </tr></thead>
          <tbody id="env-body"></tbody></table>
        </div>
      </div>

      <!-- DEPLOYMENTS -->
      <div id="page-deployments" class="hidden">
        <div class="stats" id="dep-stats"></div>
        <div class="table-wrap">
          <div class="table-header">
            <h3>All Deployments</h3>
            <div class="search-box">🔍 <input type="text" id="dep-search" placeholder="Search…" oninput="filterTable('deployments')"/></div>
          </div>
          <table><thead><tr>
            <th>Version</th><th>Status</th><th>Environment</th><th>Deployed At</th><th>Actions</th>
          </tr></thead>
          <tbody id="dep-body"></tbody></table>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- MODAL -->
<div id="modal" class="modal-backdrop hidden">
  <div class="modal">
    <div class="modal-title" id="modal-title">New Item</div>
    <div id="modal-body"></div>
    <div class="modal-actions">
      <button class="btn-cancel" onclick="closeModal()">Cancel</button>
      <button class="btn-submit" id="modal-submit" onclick="submitModal()">Save</button>
    </div>
  </div>
</div>

<!-- TOAST -->
<div id="toast"></div>

<script>
// ══════════════════════════════════════════════════════════════════
// STATE
// ══════════════════════════════════════════════════════════════════
let token = null;
let currentUser = null;
let currentPage = 'projects';
let allData = { projects:[], teams:[], environments:[], deployments:[] };
let modalMode = null; // {entity, action:'create'|'edit', item?}

const BASE = '';

// ══════════════════════════════════════════════════════════════════
// BOOT
// ══════════════════════════════════════════════════════════════════
(function boot() {
  const saved = localStorage.getItem('idp_token');
  const savedUser = localStorage.getItem('idp_user');
  if (saved && savedUser) {
    token = saved;
    currentUser = JSON.parse(savedUser);
    showShell();
  } else {
    // check hash for register link from landing
    const hash = location.hash.replace('#','');
    showAuth(hash === 'register' ? 'register' : 'login');
  }
})();

// ══════════════════════════════════════════════════════════════════
// AUTH ROUTING
// ══════════════════════════════════════════════════════════════════
function showAuth(view = 'login') {
  document.getElementById('auth-wrap').style.display = 'flex';
  document.getElementById('shell').style.display = 'none';
  showView(view);
}

function showView(v) {
  document.getElementById('view-login').classList.toggle('hidden', v !== 'login');
  document.getElementById('view-register').classList.toggle('hidden', v !== 'register');
}

function showShell() {
  document.getElementById('auth-wrap').style.display = 'none';
  document.getElementById('shell').style.display = 'flex';
  document.getElementById('user-name-display').textContent = currentUser.username;
  document.getElementById('user-role-display').textContent = currentUser.role || 'Member';
  document.getElementById('user-avatar').textContent = (currentUser.username || '?')[0].toUpperCase();
  navigate('projects');
}

// ══════════════════════════════════════════════════════════════════
// LOGIN
// ══════════════════════════════════════════════════════════════════
async function doLogin() {
  const btn = document.getElementById('login-btn');
  const errEl = document.getElementById('login-err');
  const u = document.getElementById('login-user').value.trim();
  const p = document.getElementById('login-pass').value;
  errEl.style.display = 'none';
  if (!u || !p) { showErr(errEl, 'Username and password are required.'); return; }
  btn.disabled = true; btn.textContent = 'Signing in…';
  try {
    const res = await fetch(BASE + '/api/auth/login', {
      method:'POST', headers:{'Content-Type':'application/json'},
      body: JSON.stringify({ username: u, password: p })
    });
    const data = await res.json();
    if (!res.ok) { showErr(errEl, data.message || 'Invalid credentials.'); return; }
    token = data.token;
    currentUser = { username: data.username, role: data.role };
    localStorage.setItem('idp_token', token);
    localStorage.setItem('idp_user', JSON.stringify(currentUser));
    showShell();
  } catch(e) { showErr(errEl, 'Network error. Please try again.'); }
  finally { btn.disabled = false; btn.textContent = 'Sign in'; }
}

// ══════════════════════════════════════════════════════════════════
// REGISTER
// ══════════════════════════════════════════════════════════════════
async function doRegister() {
  const btn = document.getElementById('reg-btn');
  const errEl = document.getElementById('reg-err');
  const okEl = document.getElementById('reg-ok');
  const u = document.getElementById('reg-user').value.trim();
  const e = document.getElementById('reg-email').value.trim();
  const p = document.getElementById('reg-pass').value;
  errEl.style.display = 'none'; okEl.style.display = 'none';
  if (!u || !e || !p) { showErr(errEl, 'All fields are required.'); return; }
  if (p.length < 8) { showErr(errEl, 'Password must be at least 8 characters.'); return; }
  btn.disabled = true; btn.textContent = 'Creating account…';
  try {
    const res = await fetch(BASE + '/api/auth/register', {
      method:'POST', headers:{'Content-Type':'application/json'},
      body: JSON.stringify({ username: u, email: e, password: p })
    });
    const data = await res.json();
    if (!res.ok) { showErr(errEl, data.message || 'Registration failed.'); return; }
    okEl.textContent = '✓ Account created! Signing you in…'; okEl.style.display = 'block';
    token = data.token;
    currentUser = { username: data.username, role: data.role };
    localStorage.setItem('idp_token', token);
    localStorage.setItem('idp_user', JSON.stringify(currentUser));
    setTimeout(() => showShell(), 800);
  } catch(e2) { showErr(errEl, 'Network error. Please try again.'); }
  finally { btn.disabled = false; btn.textContent = 'Create account'; }
}

function logout() {
  token = null; currentUser = null;
  localStorage.removeItem('idp_token');
  localStorage.removeItem('idp_user');
  showAuth('login');
}

// ══════════════════════════════════════════════════════════════════
// NAVIGATION
// ══════════════════════════════════════════════════════════════════
const PAGE_TITLES = { projects:'Projects', teams:'Teams', environments:'Environments', deployments:'Deployments' };
const CREATE_LABELS = { projects:'+ New Project', teams:'+ New Team', environments:'+ New Environment', deployments:'+ New Deployment' };

function navigate(page) {
  currentPage = page;
  ['projects','teams','environments','deployments'].forEach(p => {
    document.getElementById('page-'+p).classList.toggle('hidden', p !== page);
    document.getElementById('nav-'+p).classList.toggle('active', p === page);
  });
  document.getElementById('page-title').textContent = PAGE_TITLES[page];
  document.getElementById('create-btn').textContent = CREATE_LABELS[page];
  loadPage(page);
}

// ══════════════════════════════════════════════════════════════════
// API
// ══════════════════════════════════════════════════════════════════
const ENTITY_URL = {
  projects: '/api/projects',
  teams: '/api/teams',
  environments: '/api/environments',
  deployments: '/api/deployments'
};

async function api(method, path, body) {
  const opts = { method, headers: { 'Authorization':'Bearer '+token, 'Content-Type':'application/json' } };
  if (body) opts.body = JSON.stringify(body);
  const res = await fetch(BASE + path, opts);
  if (res.status === 401) { logout(); return null; }
  if (res.status === 204) return {};
  return res.json();
}

// ══════════════════════════════════════════════════════════════════
// LOAD PAGE DATA
// ══════════════════════════════════════════════════════════════════
async function loadPage(page) {
  const body = document.getElementById(page.slice(0,-1) === 'project' ? 'proj-body'
    : page.slice(0,4) === 'team' ? 'team-body'
    : page.slice(0,3) === 'env' ? 'env-body' : 'dep-body');

  const tbodyId = { projects:'proj-body', teams:'team-body', environments:'env-body', deployments:'dep-body' }[page];
  const tbody = document.getElementById(tbodyId);
  tbody.innerHTML = '<tr class="loading-row"><td colspan="10"><div class="spinner"></div></td></tr>';

  const data = await api('GET', ENTITY_URL[page]);
  if (!data) return;

  // support both array and paged {content:[...]}
  allData[page] = Array.isArray(data) ? data : (data.content || []);
  renderPage(page);
}

function renderPage(page) {
  switch(page) {
    case 'projects':     renderProjects(); break;
    case 'teams':        renderTeams(); break;
    case 'environments': renderEnvironments(); break;
    case 'deployments':  renderDeployments(); break;
  }
}

// ── PROJECTS ──
function renderProjects() {
  const q = (document.getElementById('proj-search').value||'').toLowerCase();
  const items = allData.projects.filter(p =>
    (p.name||'').toLowerCase().includes(q) || (p.description||'').toLowerCase().includes(q));

  const stats = document.getElementById('proj-stats');
  const total = allData.projects.length;
  const active = allData.projects.filter(p => p.status === 'ACTIVE').length;
  stats.innerHTML = `
    <div class="stat-card"><div class="stat-label">Total Projects</div><div class="stat-value">${total}</div><div class="stat-sub">All projects</div></div>
    <div class="stat-card"><div class="stat-label">Active</div><div class="stat-value" style="color:var(--success)">${active}</div><div class="stat-sub">In progress</div></div>
    <div class="stat-card"><div class="stat-label">Archived</div><div class="stat-value">${total-active}</div><div class="stat-sub">Completed or paused</div></div>
  `;

  const tbody = document.getElementById('proj-body');
  if (!items.length) { tbody.innerHTML = emptyState('📁','No projects found'); return; }
  tbody.innerHTML = items.map(p => `
    <tr>
      <td><span class="name">${esc(p.name)}</span></td>
      <td>${esc(p.description||'—')}</td>
      <td>${statusBadge(p.status)}</td>
      <td><div class="row-actions">
        <button class="btn-row" onclick='openEdit("projects",${JSON.stringify(p)})'>Edit</button>
        <button class="btn-row btn-row-danger" onclick="deleteItem('projects',${p.id})">Delete</button>
      </div></td>
    </tr>`).join('');
}

// ── TEAMS ──
function renderTeams() {
  const q = (document.getElementById('team-search').value||'').toLowerCase();
  const items = allData.teams.filter(t =>
    (t.name||'').toLowerCase().includes(q) || (t.description||'').toLowerCase().includes(q));
  const tbody = document.getElementById('team-body');
  if (!items.length) { tbody.innerHTML = emptyState('👥','No teams found'); return; }
  tbody.innerHTML = items.map(t => `
    <tr>
      <td><span class="name">${esc(t.name)}</span></td>
      <td>${esc(t.description||'—')}</td>
      <td><div class="row-actions">
        <button class="btn-row" onclick='openEdit("teams",${JSON.stringify(t)})'>Edit</button>
        <button class="btn-row btn-row-danger" onclick="deleteItem('teams',${t.id})">Delete</button>
      </div></td>
    </tr>`).join('');
}

// ── ENVIRONMENTS ──
function renderEnvironments() {
  const q = (document.getElementById('env-search').value||'').toLowerCase();
  const items = allData.environments.filter(e =>
    (e.name||'').toLowerCase().includes(q) || (e.type||'').toLowerCase().includes(q));
  const tbody = document.getElementById('env-body');
  if (!items.length) { tbody.innerHTML = emptyState('🌍','No environments found'); return; }
  tbody.innerHTML = items.map(e => `
    <tr>
      <td><span class="name">${esc(e.name)}</span></td>
      <td>${envTypeBadge(e.type)}</td>
      <td>${esc(e.project ? e.project.name : (e.projectId||'—'))}</td>
      <td><div class="row-actions">
        <button class="btn-row" onclick='openEdit("environments",${JSON.stringify(e)})'>Edit</button>
        <button class="btn-row btn-row-danger" onclick="deleteItem('environments',${e.id})">Delete</button>
      </div></td>
    </tr>`).join('');
}

// ── DEPLOYMENTS ──
function renderDeployments() {
  const q = (document.getElementById('dep-search').value||'').toLowerCase();
  const items = allData.deployments.filter(d =>
    (d.version||'').toLowerCase().includes(q) || (d.status||'').toLowerCase().includes(q));

  const stats = document.getElementById('dep-stats');
  const total = allData.deployments.length;
  const success = allData.deployments.filter(d => d.status === 'SUCCESS').length;
  const failed = allData.deployments.filter(d => d.status === 'FAILED').length;
  stats.innerHTML = `
    <div class="stat-card"><div class="stat-label">Total Deploys</div><div class="stat-value">${total}</div><div class="stat-sub">All time</div></div>
    <div class="stat-card"><div class="stat-label">Successful</div><div class="stat-value" style="color:var(--success)">${success}</div><div class="stat-sub">Completed</div></div>
    <div class="stat-card"><div class="stat-label">Failed</div><div class="stat-value" style="color:var(--danger)">${failed}</div><div class="stat-sub">Need attention</div></div>
  `;

  const tbody = document.getElementById('dep-body');
  if (!items.length) { tbody.innerHTML = emptyState('🚀','No deployments yet'); return; }
  tbody.innerHTML = items.map(d => `
    <tr>
      <td><span class="name">${esc(d.version||'—')}</span></td>
      <td>${depStatusBadge(d.status)}</td>
      <td>${esc(d.environment ? d.environment.name : (d.environmentId||'—'))}</td>
      <td>${d.deployedAt ? new Date(d.deployedAt).toLocaleString() : '—'}</td>
      <td><div class="row-actions">
        <button class="btn-row" onclick='openEdit("deployments",${JSON.stringify(d)})'>Edit</button>
        <button class="btn-row btn-row-danger" onclick="deleteItem('deployments',${d.id})">Delete</button>
      </div></td>
    </tr>`).join('');
}

// ══════════════════════════════════════════════════════════════════
// CRUD MODALS
// ══════════════════════════════════════════════════════════════════
function openCreate() { buildModal(currentPage, 'create', null); }
function openEdit(entity, item) { buildModal(entity, 'edit', item); }

function buildModal(entity, action, item) {
  modalMode = { entity, action, item };
  document.getElementById('modal-title').textContent =
    action === 'create' ? 'New ' + PAGE_TITLES[entity].slice(0,-1) : 'Edit ' + PAGE_TITLES[entity].slice(0,-1);
  document.getElementById('modal-body').innerHTML = modalForm(entity, item);
  document.getElementById('modal').classList.remove('hidden');
}

function closeModal() { document.getElementById('modal').classList.add('hidden'); }

function modalForm(entity, item) {
  const v = item || {};
  if (entity === 'projects') return `
    <div class="form-group"><label>Name</label><input type="text" id="f-name" value="${esc(v.name||'')}" placeholder="Project name"/></div>
    <div class="form-group"><label>Description</label><input type="text" id="f-desc" value="${esc(v.description||'')}" placeholder="Short description"/></div>
    <div class="form-group"><label>Status</label><select id="f-status">
      <option value="ACTIVE" ${v.status==='ACTIVE'?'selected':''}>Active</option>
      <option value="INACTIVE" ${v.status==='INACTIVE'?'selected':''}>Inactive</option>
      <option value="ARCHIVED" ${v.status==='ARCHIVED'?'selected':''}>Archived</option>
    </select></div>`;

  if (entity === 'teams') return `
    <div class="form-group"><label>Name</label><input type="text" id="f-name" value="${esc(v.name||'')}" placeholder="Team name"/></div>
    <div class="form-group"><label>Description</label><input type="text" id="f-desc" value="${esc(v.description||'')}" placeholder="What does this team own?"/></div>`;

  if (entity === 'environments') return `
    <div class="form-group"><label>Name</label><input type="text" id="f-name" value="${esc(v.name||'')}" placeholder="e.g. production"/></div>
    <div class="form-group"><label>Type</label><select id="f-type">
      <option value="DEVELOPMENT" ${v.type==='DEVELOPMENT'?'selected':''}>Development</option>
      <option value="STAGING" ${v.type==='STAGING'?'selected':''}>Staging</option>
      <option value="PRODUCTION" ${v.type==='PRODUCTION'?'selected':''}>Production</option>
    </select></div>
    <div class="form-group"><label>Project ID</label><input type="text" id="f-projectId" value="${esc(String(v.projectId||''))}" placeholder="Project numeric ID"/></div>`;

  if (entity === 'deployments') return `
    <div class="form-group"><label>Version</label><input type="text" id="f-version" value="${esc(v.version||'')}" placeholder="e.g. v1.2.3"/></div>
    <div class="form-group"><label>Status</label><select id="f-dep-status">
      <option value="PENDING" ${v.status==='PENDING'?'selected':''}>Pending</option>
      <option value="IN_PROGRESS" ${v.status==='IN_PROGRESS'?'selected':''}>In Progress</option>
      <option value="SUCCESS" ${v.status==='SUCCESS'?'selected':''}>Success</option>
      <option value="FAILED" ${v.status==='FAILED'?'selected':''}>Failed</option>
    </select></div>
    <div class="form-group"><label>Environment ID</label><input type="text" id="f-envId" value="${esc(String(v.environmentId||''))}" placeholder="Environment numeric ID"/></div>`;
  return '';
}

async function submitModal() {
  const { entity, action, item } = modalMode;
  let body = {};
  if (entity === 'projects') body = { name: fv('f-name'), description: fv('f-desc'), status: fv('f-status') };
  else if (entity === 'teams') body = { name: fv('f-name'), description: fv('f-desc') };
  else if (entity === 'environments') body = { name: fv('f-name'), type: fv('f-type'), projectId: parseInt(fv('f-projectId')) };
  else if (entity === 'deployments') body = { version: fv('f-version'), status: fv('f-dep-status'), environmentId: parseInt(fv('f-envId')) };

  const url = action === 'edit' ? `${ENTITY_URL[entity]}/${item.id}` : ENTITY_URL[entity];
  const method = action === 'edit' ? 'PUT' : 'POST';
  const res = await api(method, url, body);
  if (!res) return;
  closeModal();
  toast('Saved successfully', 'ok');
  loadPage(entity);
}

async function deleteItem(entity, id) {
  if (!confirm('Delete this item?')) return;
  await api('DELETE', `${ENTITY_URL[entity]}/${id}`);
  toast('Deleted', 'ok');
  loadPage(entity);
}

// ══════════════════════════════════════════════════════════════════
// FILTER
// ══════════════════════════════════════════════════════════════════
function filterTable(page) { renderPage(page); }

// ══════════════════════════════════════════════════════════════════
// HELPERS
// ══════════════════════════════════════════════════════════════════
function fv(id) { const el = document.getElementById(id); return el ? el.value : ''; }
function esc(s) { return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;'); }
function emptyState(icon, msg) { return `<tr><td colspan="10"><div class="empty-state"><div class="empty-icon">${icon}</div><p>${msg}</p></div></td></tr>`; }
function showErr(el, msg) { el.textContent = msg; el.style.display = 'block'; }

function statusBadge(s) {
  const map = { ACTIVE:'badge-green', INACTIVE:'badge-gray', ARCHIVED:'badge-yellow' };
  return `<span class="badge ${map[s]||'badge-gray'}">${s||'—'}</span>`;
}
function envTypeBadge(t) {
  const map = { PRODUCTION:'badge-red', STAGING:'badge-yellow', DEVELOPMENT:'badge-blue' };
  return `<span class="badge ${map[t]||'badge-gray'}">${t||'—'}</span>`;
}
function depStatusBadge(s) {
  const map = { SUCCESS:'badge-green', FAILED:'badge-red', IN_PROGRESS:'badge-blue', PENDING:'badge-gray' };
  return `<span class="badge ${map[s]||'badge-gray'}">${s||'—'}</span>`;
}

function toast(msg, type='ok') {
  const el = document.createElement('div');
  el.className = `toast toast-${type}`;
  el.innerHTML = (type==='ok'?'✓':'✗') + ' ' + msg;
  document.getElementById('toast').appendChild(el);
  setTimeout(() => el.remove(), 3500);
}

// enter key on auth inputs
document.addEventListener('keydown', e => {
  if (e.key === 'Enter') {
    if (!document.getElementById('view-login').classList.contains('hidden')) doLogin();
    else if (!document.getElementById('view-register').classList.contains('hidden')) doRegister();
  }
});

// close modal on backdrop click
document.getElementById('modal').addEventListener('click', e => {
  if (e.target === document.getElementById('modal')) closeModal();
});
</script>
</body>
</html>
""";
    }
}
