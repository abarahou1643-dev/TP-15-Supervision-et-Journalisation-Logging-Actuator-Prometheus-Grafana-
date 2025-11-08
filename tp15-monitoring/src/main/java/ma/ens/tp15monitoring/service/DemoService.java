package ma.ens.tp15monitoring.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);
    private final Counter tp15Counter = Metrics.counter("tp15.requests.total");

    public String processData() {
        tp15Counter.increment();
        logger.info("TP15 - Démarrage du traitement des données");
        
        try {
            Thread.sleep(200 + (long)(Math.random() * 300));
        } catch (InterruptedException e) {
            logger.error("TP15 - Erreur dans le traitement", e);
            Thread.currentThread().interrupt();
        }
        
        logger.info("TP15 - Traitement terminé avec succès");
        return "Data processed by TP15 service - " + System.currentTimeMillis();
    }
}
