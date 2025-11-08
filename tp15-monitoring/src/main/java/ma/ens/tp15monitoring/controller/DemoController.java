package ma.ens.tp15monitoring.controller;

import ma.ens.tp15monitoring.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private final DemoService service;

    public DemoController(DemoService service) {
        this.service = service;
    }

    @GetMapping("/process")
    public String process() {
        return service.processData();
    }

    @GetMapping("/hello")
    public String hello() {
        return "TP15 Monitoring App - Hello World!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "TP15 Application is UP and RUNNING!";
    }
    
    @GetMapping("/metrics-info")
    public String metricsInfo() {
        return "Les m?triques sont disponibles sur: http://localhost:8080/actuator/prometheus";
    }
}
