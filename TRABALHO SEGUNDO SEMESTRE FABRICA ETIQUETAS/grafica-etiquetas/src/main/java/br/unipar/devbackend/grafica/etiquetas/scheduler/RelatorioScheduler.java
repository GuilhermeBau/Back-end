package br.unipar.devbackend.grafica.etiquetas.scheduler;

import br.unipar.devbackend.grafica.etiquetas.service.EtiquetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RelatorioScheduler {

    private static final Logger log = LoggerFactory.getLogger(RelatorioScheduler.class);

    private final EtiquetaService service;

    public RelatorioScheduler(EtiquetaService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 15000)
    public void logQuantidade() {
        log.info("Total de etiquetas cadastradas: {}", service.listarTodas().size());
    }

    @Scheduled(cron = "0 * * * * *")
    public void gerarRelatorio() {
        log.info("Tarefa CRON executada: relatório simples de etiquetas.");
    }
}
