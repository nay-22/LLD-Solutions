package Hard.StockBroker.src.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.StockBroker.src.domain.Portfolio;
import Hard.StockBroker.src.exception.ResourceNotFoundException;

public class PortfolioManager {

    private final Map<String, Map<String, Portfolio>> portfolios;

    private static final class Singleton {
        private static final PortfolioManager INSTANCE = new PortfolioManager();
    }

    private PortfolioManager() {
        this.portfolios = new ConcurrentHashMap<>();
    }

    public static PortfolioManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void register(String userId) {
        portfolios.put(userId, new ConcurrentHashMap<>());
    }

    public Portfolio createPortfolio(String userId, String name) throws ResourceNotFoundException {
        if (!portfolios.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        Portfolio portfolio = new Portfolio("portfolio-" + userId, userId, name);
        portfolios.get(userId).put(portfolio.getId(), portfolio);
        return portfolio;
    }

    public void deletePortfolio(String userId, String portfolioId) throws ResourceNotFoundException {
        if (!portfolios.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        // TODO: flush all stock metas by placing sell order and delete it when
        // stockmetas is empty
    }

    public Portfolio getPortfolio(String userId, String portfolioId) throws ResourceNotFoundException {
        if (!portfolios.containsKey(userId) || !portfolios.get(userId).containsKey(portfolioId)) {
            throw new ResourceNotFoundException("The portfolio with specified identifiers does not exists!");
        }
        return portfolios.get(userId).get(portfolioId);
    }

    public List<Portfolio> getPortfoliosByUserId(String userId) throws ResourceNotFoundException {
        if (!portfolios.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        return List.copyOf(portfolios.getOrDefault(userId, Map.of()).values());
    }

    public List<Portfolio> getAll() throws ResourceNotFoundException {
        return portfolios.values().stream().flatMap(p -> p.values().stream()).toList();
    }

}
