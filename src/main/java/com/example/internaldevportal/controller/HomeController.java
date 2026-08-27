package com.example.internaldevportal.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Internal Developer Portal</title>
  <style>
    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
      background: #0f1117;
      color: #e2e8f0;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }
    header {
      padding: 1.5rem 2rem;
      border-bottom: 1px solid #1e2535;
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }
    header .logo {
      width: 36px; height: 36px;
      background: linear-gradient(135deg, #6366f1, #8b5cf6);
      border-radius: 8px;
      display: flex; align-items: center; justify-content: center;
      font-size: 1.1rem; font-weight: 700; color: #fff;
    }
    header h1 { font-size: 1.1rem; font-weight: 600; color: #f1f5f9; }
    header span { font-size: 0.75rem; color: #64748b; margin-left: 0.5rem; }
    main {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 4rem 1.5rem;
      text-align: center;
    }
    .badge {
      display: inline-flex; align-items: center; gap: 0.4rem;
      background: #1e2535; border: 1px solid #2d3748;
      border-radius: 100px; padding: 0.3rem 0.85rem;
      font-size: 0.78rem; color: #94a3b8; margin-bottom: 2rem;
    }
    .badge .dot { width: 6px; height: 6px; border-radius: 50%; background: #22c55e; }
    h2 {
      font-size: clamp(2rem, 5vw, 3rem);
      font-weight: 700;
      background: linear-gradient(135deg, #f1f5f9, #94a3b8);
      -webkit-background-clip: text; -webkit-text-fill-color: transparent;
      background-clip: text;
      line-height: 1.2; margin-bottom: 1rem;
    }
    p.sub {
      font-size: 1.05rem; color: #64748b;
      max-width: 520px; line-height: 1.7; margin-bottom: 2.5rem;
    }
    .actions { display: flex; gap: 1rem; flex-wrap: wrap; justify-content: center; }
    .btn {
      display: inline-flex; align-items: center; gap: 0.5rem;
      padding: 0.7rem 1.5rem; border-radius: 8px;
      font-size: 0.92rem; font-weight: 500; text-decoration: none;
      transition: all 0.15s ease; cursor: pointer; border: none;
    }
    .btn-primary {
      background: linear-gradient(135deg, #6366f1, #8b5cf6);
      color: #fff;
    }
    .btn-primary:hover { opacity: 0.88; transform: translateY(-1px); }
    .btn-secondary {
      background: #1e2535; color: #cbd5e1;
      border: 1px solid #2d3748;
    }
    .btn-secondary:hover { background: #263145; transform: translateY(-1px); }
    .cards {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 1rem; max-width: 900px; width: 100%;
      margin-top: 4rem;
    }
    .card {
      background: #141824; border: 1px solid #1e2535;
      border-radius: 12px; padding: 1.5rem;
      text-align: left; transition: border-color 0.15s;
    }
    .card:hover { border-color: #6366f1; }
    .card-icon {
      font-size: 1.4rem; margin-bottom: 0.75rem;
    }
    .card h3 { font-size: 0.95rem; font-weight: 600; color: #f1f5f9; margin-bottom: 0.35rem; }
    .card p { font-size: 0.82rem; color: #64748b; line-height: 1.5; }
    footer {
      text-align: center; padding: 1.5rem;
      font-size: 0.78rem; color: #334155;
      border-top: 1px solid #1e2535;
    }
    footer a { color: #6366f1; text-decoration: none; }
    footer a:hover { text-decoration: underline; }
  </style>
</head>
<body>
  <header>
    <div class="logo">IDP</div>
    <h1>Internal Developer Portal</h1>
    <span>v1.0</span>
  </header>

  <main>
    <div class="badge">
      <span class="dot"></span>
      All systems operational
    </div>

    <h2>Your Engineering<br/>Command Centre</h2>
    <p class="sub">
      Manage projects, teams, environments and deployments in one place.
      Register to get started or explore the API documentation.
    </p>

    <div class="actions">
      <a href="/swagger-ui/index.html" class="btn btn-primary">
        &#128196; Explore API
      </a>
      <a href="/actuator/health" class="btn btn-secondary">
        &#10084; Health Status
      </a>
    </div>

    <div class="cards">
      <div class="card">
        <div class="card-icon">&#128218;</div>
        <h3>Projects</h3>
        <p>Track all your engineering projects with metadata, ownership and status.</p>
      </div>
      <div class="card">
        <div class="card-icon">&#128101;</div>
        <h3>Teams</h3>
        <p>Organise engineers into teams and assign them to projects.</p>
      </div>
      <div class="card">
        <div class="card-icon">&#127759;</div>
        <h3>Environments</h3>
        <p>Manage dev, staging and production environments per project.</p>
      </div>
      <div class="card">
        <div class="card-icon">&#128640;</div>
        <h3>Deployments</h3>
        <p>Record and audit every deployment with status and timestamps.</p>
      </div>
    </div>
  </main>

  <footer>
    Internal Developer Portal &mdash;
    <a href="/swagger-ui/index.html">API Docs</a> &middot;
    <a href="/actuator/health">Health</a>
  </footer>
</body>
</html>
""";
    }
}
