#  TP15 - Monitoring Spring Boot avec Actuator, Prometheus et Grafana

##  Objectif du TP
Mettre en place une stack complète de monitoring pour une application Spring Boot avec collecte, stockage et visualisation des métriques.

##  Table des matières
- [Architecture](#-architecture)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [Démonstrations](#-démonstrations)
- [Structure du projet](#-structure-du-projet)

##  Architecture

```
Application Spring Boot (8080)
        ↓
   Spring Boot Actuator
        ↓
    Métriques Prometheus
        ↓
   Prometheus (9090) - Collecte
        ↓
   Grafana (3000) - Visualisation
```

##  Prérequis

- **Java 17+**
- **Maven 3.6+**
- **PowerShell** (Windows)
- **Navigateur web**

## Installation

### 1. Cloner et configurer le projet
```powershell
# Se positionner dans le dossier du projet
cd C:\Users\sweet\Desktop\tp15-monitoring

# Vérifier la structure
tree /F
```

### 2. Démarrer l'application Spring Boot
```powershell
mvn spring-boot:run
```

### 3. Démarrer Prometheus
```powershell
cd C:\Users\sweet\Desktop\monitoring\prometheus
.\prometheus.exe --config.file=prometheus.yml
```

### 4. Démarrer Grafana
```powershell
cd C:\Users\sweet\Desktop\monitoring\grafana\bin
.\grafana-server.exe
```

## Utilisation

### Accès aux services
- **Application Spring Boot** : http://localhost:8080
- **Prometheus** : http://localhost:9090
- **Grafana** : http://localhost:3000

###  Endpoints de test
```bash
# Santé de l'application
curl http://localhost:8080/health

# Hello World
curl http://localhost:8080/hello

# Traitement avec métriques
curl http://localhost:8080/process

# Informations métriques
curl http://localhost:8080/metrics-info
```

###  Métriques Actuator
```bash
# Santé complète
curl http://localhost:8080/actuator/health

# Informations application
curl http://localhost:8080/actuator/info

# Métriques Prometheus
curl http://localhost:8080/actuator/prometheus

# Liste des métriques
curl http://localhost:8080/actuator/metrics
```

##  Démonstrations



https://github.com/user-attachments/assets/925f47db-49fb-458d-8795-05e990df1e84



###  Application Spring Boot Fonctionnelle
**URL : http://localhost:8080/health**

<img width="959" height="503" alt="tp151" src="https://github.com/user-attachments/assets/20def021-e667-4372-badb-7737526e225f" />



**URL : http://localhost:8080/process**

<img width="956" height="504" alt="tp152" src="https://github.com/user-attachments/assets/35ab006d-a9bc-4d52-8055-415ec62d0994" />



###  Spring Boot Actuator
**URL : http://localhost:8080/actuator/health**

<img width="952" height="463" alt="tp153" src="https://github.com/user-attachments/assets/cf034881-0175-41fb-8607-d3e66904f738" />


###  Métriques Prometheus
**URL : http://localhost:8080/actuator/prometheus**

<img width="953" height="473" alt="tp154" src="https://github.com/user-attachments/assets/c48493f3-3a78-458a-a07d-7c4aadc52494" />



###  Interface Prometheus
**URL : http://localhost:9090**

**Requêtes à tester dans Prometheus :**

# Votre métrique personnalisée

<img width="959" height="429" alt="tp1551" src="https://github.com/user-attachments/assets/05cf87cf-92db-4f81-8ebf-fa0eabe79497" />


# Métriques HTTP Spring Boot

<img width="959" height="415" alt="tp1552" src="https://github.com/user-attachments/assets/278c0f07-aa9e-4cc0-b588-3c81168a9dc2" />


# Taux de requêtes par seconde

<img width="956" height="394" alt="tp553" src="https://github.com/user-attachments/assets/173a6c4c-18e4-4347-aa65-af97d5c47bbf" />



# Mémoire JVM utilisée

<img width="957" height="391" alt="tp555" src="https://github.com/user-attachments/assets/7aa50070-669a-47c1-b434-e8e2cf3823b6" />



###  Dashboard Grafana
**URL : http://localhost:3000**

**Configuration :**
1. **Data Source** → Prometheus-TP15 → http://localhost:9090
2. **Import Dashboard** → ID: 4701 (Spring Boot Statistics)

**Écran Grafana**



https://github.com/user-attachments/assets/0e74007f-3440-4142-bedf-47fa433cf177



### Génération de Trafic
**Script PowerShell de test :**
```powershell
cd C:\Users\sweet\Desktop\tp15-monitoring

Write-Host "=== GÉNÉRATION DE TRAFIC ===" -ForegroundColor Cyan

for ($i = 1; $i -le 10; $i++) {
    $timestamp = Get-Date -Format "HH:mm:ss"
    Write-Host "[$timestamp] Requête #$i" -ForegroundColor Yellow
    
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:8080/process" -TimeoutSec 2
        Write-Host "   Traitement réussi" -ForegroundColor Green
    } catch {
        Write-Host "   Erreur" -ForegroundColor Red
    }
    
    Start-Sleep -Seconds 2
}
```

**Résultat attendu dans Grafana :**
-  Courbe `tp15_requests_total` qui monte
- Taux HTTP qui augmente temporairement
- Mémoire JVM qui fluctue
- CPU qui montre une légère activité

##  Structure du Projet

```
tp15-monitoring/
├── src/
│   └── main/
│       ├── java/
│       │   └── ma/
│       │       └── ens/
│       │           └── tp15monitoring/
│       │               ├── Tp15MonitoringApplication.java
│       │               ├── controller/
│       │               │   └── DemoController.java
│       │               └── service/
│       │                   └── DemoService.java
│       └── resources/
│           └── application.properties
├── monitoring/
│   ├── prometheus/
│   │   ├── prometheus.yml
│   │   └── data/
│   └── grafana/
│       └── bin/
│           └── grafana-server.exe
├── logs/
│   └── tp15-application.log
└── pom.xml
```

##  Fichiers de Configuration

### application.properties
```properties
server.port=8080
spring.application.name=tp15-monitoring

# Actuator
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

# Métriques personnalisées
info.app.name=TP15 Monitoring App
info.app.version=1.0.0

# Logging
logging.file.name=logs/tp15-application.log
logging.level.ma.ens.tp15monitoring=DEBUG
```

### prometheus.yml
```yaml
global:
  scrape_interval: 5s

scrape_configs:
  - job_name: 'tp15-spring-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
    scrape_interval: 5s
```

##  Métriques Clés Surveillées

### Métriques Personnalisées TP15
- `tp15_requests_total` : Nombre total de requêtes traitées

### Métriques Spring Boot Actuator
- `http_server_requests_seconds_count` : Compteur de requêtes HTTP
- `jvm_memory_used_bytes` : Mémoire JVM utilisée
- `process_cpu_usage` : Utilisation du CPU
- `jvm_threads_live_threads` : Threads actifs
- `logback_events_total` : Événements de log

### Métriques Système
- `system_cpu_usage` : CPU système
- `disk_free_bytes` : Espace disque libre
- `tomcat_sessions_active_current_sessions` : Sessions Tomcat

##  Commandes Utiles

### Vérification de l'état
```powershell
# Vérifier que tous les services tournent
try {
    Invoke-RestMethod -Uri "http://localhost:8080/health"
    Write-Host " Application OK" -ForegroundColor Green
} catch { Write-Host " Application" -ForegroundColor Red }

try {
    Invoke-WebRequest -Uri "http://localhost:9090" -TimeoutSec 2
    Write-Host " Prometheus OK" -ForegroundColor Green
} catch { Write-Host " Prometheus" -ForegroundColor Red }

try {
    Invoke-WebRequest -Uri "http://localhost:3000" -TimeoutSec 2
    Write-Host " Grafana OK" -ForegroundColor Green
} catch { Write-Host " Grafana" -ForegroundColor Red }
```

### Surveillance en Temps Réel
```powershell
# Surveillance des logs
Get-Content "logs/tp15-application.log" -Tail 10 -Wait

# Surveillance des métriques
while ($true) {
    try {
        $metrics = Invoke-WebRequest -Uri "http://localhost:8080/actuator/prometheus" -TimeoutSec 2
        $tp15 = ($metrics.Content -split "`n" | Where-Object { $_ -match "^tp15_requests_total " })
        Write-Host "$(Get-Date -Format 'HH:mm:ss') $tp15" -ForegroundColor Cyan
    } catch {
        Write-Host "$(Get-Date -Format 'HH:mm:ss')  Métriques indisponibles" -ForegroundColor Red
    }
    Start-Sleep -Seconds 5
}
```

##  Conclusion

Ce TP démontre une stack de monitoring complète et professionnelle pour applications Spring Boot. La solution permet :

-  **Surveillance en temps réel** des performances
-  **Détection proactive** des problèmes
-  **Visualisation intuitive** avec Grafana
-  **Métriques personnalisées** adaptées aux besoins métier
-  **Architecture scalable** prête pour la production

