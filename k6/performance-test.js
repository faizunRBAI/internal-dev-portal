import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

const errorRate = new Rate('error_rate');

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const PERF_USERNAME = __ENV.PERF_USERNAME || 'k6perfuser';
const PERF_EMAIL = __ENV.PERF_EMAIL || 'k6perf@example.com';
const PERF_PASS = __ENV.PERF_PASS || 'PerfTest123Xk6';

export const options = {
    stages: [
        { duration: '30s', target: 5 },
        { duration: '2m', target: 20 },
        { duration: '30s', target: 0 },
    ],
    thresholds: {
        'http_req_duration': ['p(95)<500'],
        'error_rate': ['rate<0.01'],
        'http_req_failed': ['rate<0.01'],
    },
};

export function setup() {
    const headers = { 'Content-Type': 'application/json' };

    // Register (idempotent — 409 is acceptable)
    http.post(`${BASE_URL}/api/auth/register`, JSON.stringify({
        username: PERF_USERNAME,
        email: PERF_EMAIL,
        password: PERF_PASS,
    }), { headers });

    // Login
    const loginRes = http.post(`${BASE_URL}/api/auth/login`, JSON.stringify({
        username: PERF_USERNAME,
        password: PERF_PASS,
    }), { headers });

    if (loginRes.status === 200) {
        return { token: JSON.parse(loginRes.body).token };
    }
    return { token: '' };
}

export default function (data) {
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${data.token}`,
    };

    const health = http.get(`${BASE_URL}/actuator/health`);
    check(health, { 'health 200': (r) => r.status === 200 });
    errorRate.add(health.status !== 200);

    const projects = http.get(`${BASE_URL}/api/projects`, { headers });
    check(projects, { 'projects 200': (r) => r.status === 200 });
    errorRate.add(projects.status !== 200);

    const teams = http.get(`${BASE_URL}/api/teams`, { headers });
    check(teams, { 'teams 200': (r) => r.status === 200 });
    errorRate.add(teams.status !== 200);

    const envs = http.get(`${BASE_URL}/api/environments`, { headers });
    check(envs, { 'environments 200': (r) => r.status === 200 });
    errorRate.add(envs.status !== 200);

    const deployments = http.get(`${BASE_URL}/api/deployments`, { headers });
    check(deployments, { 'deployments 200': (r) => r.status === 200 });
    errorRate.add(deployments.status !== 200);

    sleep(1);
}
