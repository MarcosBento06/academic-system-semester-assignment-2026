package br.com.sistema.logging;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

// TUS-2395 - Verify logging infrastructure behavior
class AcademicLoggerTest {

    @Test
    void getLoggerRetornaInstanciaValida() {
        Logger logger = AcademicLogger.getLogger();
        assertNotNull(logger);
    }

    @Test
    void getLoggerRetornaMesmaInstanciaEmChamadasSubsequentes() {
        Logger l1 = AcademicLogger.getLogger();
        Logger l2 = AcademicLogger.getLogger();
        assertSame(l1, l2);
    }

    @Test
    void loggerPossuiHandlersConfigurados() {
        Logger logger = AcademicLogger.getLogger();
        assertTrue(logger.getHandlers().length > 0);
    }

    @Test
    void loggerAceitaMensagensInfoSemErro() {
        Logger logger = AcademicLogger.getLogger();
        assertDoesNotThrow(() -> logger.info("TEST - mensagem de teste"));
    }
}
