# internal-dev-portal — Working Notes

## Project
- Cloud: AWS us-east-1 | Target: EC2 (t3.medium, Ubuntu 22.04) | VCS: GitHub
- Stack: Spring Boot 3 / Java 21, PostgreSQL 16 (Docker), Nginx, Docker, Terraform, Puppet, Ansible
- Status: Design phase — awaiting design approval

## Architecture Decisions
- **EC2 t3.medium**: Java 21 JVM + PostgreSQL + Docker needs 4 GB RAM headroom
- **PostgreSQL in Docker**: Tier-1 single-box; NOT RDS (user requested EC2-hosted infra)
- **GHCR**: Image registry — GITHUB_TOKEN in CI for push; GHCR_TOKEN secret for EC2 docker pull
- **Puppet bootstrap**: installed via Ansible bootstrap.yml (copies manifests, runs puppet apply)
- **Ansible configure**: deploys Nginx + Spring Boot Docker container + env vars
- **Self-sufficient jobs**: every stage that needs the instance IP reads from terraform state directly (no job output threading)
- **Custom VPC**: user explicitly requested VPC in Terraform (not default VPC reuse)

## Pipeline Stages (in order)
1. lint — Checkstyle
2. test — JUnit unit + integration (TestContainers + PostgreSQL)
3. security — SpotBugs + OWASP Dependency Check
4. build-push — Maven JAR + Docker build + GHCR push
5. provision — Terraform (VPC, Subnet, IGW, RT, SG, EC2, EIP, IAM)
6. puppet-bootstrap (custom) — installs Puppet, applies site.pp (Java, Docker, users, hardening)
7. configure — Ansible site.yml (Nginx, Docker pull, container start, .env)
8. verify — /actuator/health retry curl
Extra: performance workflow — k6 (workflow_dispatch)

## Secrets Required (to set after repo push)
- DB_PASSWORD: alphanumeric ≥20 chars (generated)
- JWT_SECRET: alphanumeric ≥32 chars (generated)
- GHCR_TOKEN: GitHub PAT with packages:read scope (user must create)
- Platform sets: PROJECT_NAME, TF_STATE_BUCKET, SSH_USER, SSH_PRIVATE_KEY, SSH_PUBLIC_KEY, AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY

## Terraform Resources
- aws_vpc, aws_subnet (public), aws_internet_gateway, aws_route_table, aws_route_table_association
- aws_security_group (22/80/443 inbound)
- aws_key_pair (from SSH_PUBLIC_KEY)
- aws_instance (t3.medium, Ubuntu 22.04, ami data source)
- aws_eip + aws_eip_association
- aws_iam_role + aws_iam_instance_profile (SSM + CloudWatch)

## Puppet Manifests (puppet/manifests/site.pp)
- Class: java_install → openjdk-21-jdk
- Class: docker_install → docker.io + service enabled
- Class: system_users → appuser in docker group
- Class: os_hardening → SSH hardening, disable root login

## Ansible Playbooks
- ansible/bootstrap.yml: copies puppet/ dir, installs puppet-agent (jammy), runs puppet apply
- ansible/site.yml: Nginx install + vhost config (proxy_pass :8080), GHCR docker login (no_log), docker pull, docker run (PostgreSQL + app containers), wait_for :8080

## Spring Boot App Structure
- Entities: Project, Team, Environment, Deployment, AppUser
- CRUD controllers for all 4 domain entities
- JWT auth: /api/auth/login + /api/auth/register → Bearer token
- /actuator/health exposed without auth
- OpenAPI via springdoc-openapi-starter-webmvc-ui
- Flyway migrations

## Tests
- Unit: service layer (Mockito), JUnit 5, tagged @Tag("unit")
- Integration: @SpringBootTest + Testcontainers PostgreSQL, tagged @Tag("integration")
- API: MockMvc in unit tests
- Performance: k6/performance-test.js (GET /actuator/health + CRUD endpoints)

## Known Constraints
- OWASP NVD download can be slow (60 min timeout set)
- openjdk-21-jdk available in Ubuntu 22.04 main repos ✓
- Puppet 8 Jammy release available from apt.puppet.com ✓
- community.general installed in puppet-bootstrap stage (ansible.builtin only in site.yml, docker via shell commands)

## TODO
- [ ] Design approval
- [ ] Plan approval
- [ ] Generate all files
- [ ] Set pipeline secrets (DB_PASSWORD, JWT_SECRET, GHCR_TOKEN)
- [ ] Create repo + push
- [ ] Deploy
