# Internal Developer Portal

> Enterprise-grade platform for managing Projects, Teams, Environments and Deployments.
> Built with Spring Boot 3, Java 21, PostgreSQL, Docker — deployed to AWS EC2 via GitHub Actions.

## Architecture

```
Developer → Elastic IP → Nginx :80 → Spring Boot :8080 → PostgreSQL :5432
                                                ↑
               GitHub Actions CI/CD ────────────┘
               (Terraform + Puppet + Ansible)
```

**Stack:** Spring Boot 3.3 · Java 21 · PostgreSQL 16 · Docker · Nginx  
**IaC:** Terraform 5.x (AWS VPC, EC2 t3.medium, EIP, IAM)  
**Config:** Puppet 8 (bootstrap) + Ansible (deploy)  
**CI/CD:** GitHub Actions → GHCR → AWS EC2  

## API Endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Get JWT token |
| GET/POST | `/api/projects` | Bearer | List / create projects |
| GET/PUT/DELETE | `/api/projects/{id}` | Bearer | Read / update / delete project |
| GET/POST | `/api/teams` | Bearer | List / create teams |
| GET/PUT/DELETE | `/api/teams/{id}` | Bearer | Read / update / delete team |
| GET/POST | `/api/environments` | Bearer | List / create environments |
| GET/PUT/DELETE | `/api/environments/{id}` | Bearer | Read / update / delete environment |
| GET/POST | `/api/deployments` | Bearer | List / create deployments |
| PATCH | `/api/deployments/{id}/status` | Bearer | Update deployment status |
| GET | `/actuator/health` | Public | Health check |
| GET | `/swagger-ui.html` | Public | Interactive API docs |

## Prerequisites

- Java 21 + Maven
- Docker + Docker Compose
- AWS account (connected to UDAP)
- GitHub account (connected to UDAP)
- GitHub PAT with `packages:read` scope → set as `GHCR_TOKEN` secret

## Local Development

```bash
# Start PostgreSQL
docker run -d --name idpdb -e POSTGRES_DB=idpdb \
  -e POSTGRES_USER=idpuser -e POSTGRES_PASSWORD=devpassword \
  -p 5432:5432 postgres:16-alpine

# Run the application
DATABASE_URL=jdbc:postgresql://localhost:5432/idpdb \
DB_USERNAME=idpuser DB_PASSWORD=devpassword \
JWT_SECRET=localdevjwtsecretmustbe32chars!! \
./mvnw spring-boot:run
```

Open: http://localhost:8080

## Running Tests

```bash
# Unit tests
mvn test -Dgroups=unit

# Integration tests (requires Docker for Testcontainers)
mvn test -Dgroups=integration

# Full quality checks
mvn checkstyle:check spotbugs:check
```

## Secrets Required

Set these as GitHub repository secrets (UDAP sets platform secrets automatically):

| Secret | Description |
|--------|-------------|
| `DB_PASSWORD` | PostgreSQL password (alphanumeric ≥20 chars) |
| `JWT_SECRET` | JWT signing secret (alphanumeric ≥32 chars) |
| `GHCR_TOKEN` | GitHub PAT with `packages:read` for EC2 docker pull |

## Performance Testing

```bash
BASE_URL=http://<your-ip> k6 run k6/performance-test.js
```

Or trigger the `performance` workflow in GitHub Actions.

## Deployment

Handled automatically by GitHub Actions on push to `main`:
1. **lint** → Checkstyle
2. **test** → JUnit unit + integration (Testcontainers)
3. **security** → SpotBugs + OWASP Dependency Check
4. **build-push** → Maven JAR + Docker image → GHCR
5. **provision** → Terraform (VPC, EC2, EIP, IAM)
6. **puppet-bootstrap** → Install Java 21, Docker, OS hardening
7. **configure** → Ansible (Nginx, PostgreSQL container, app container)
8. **verify** → `GET /actuator/health` → HTTP 200

## Infrastructure

| Resource | Value |
|----------|-------|
| EC2 type | t3.medium (4 GB RAM) |
| OS | Ubuntu 22.04 LTS |
| Region | us-east-1 |
| VPC CIDR | 10.0.0.0/16 |
| Estimated cost | ~$35/mo |
