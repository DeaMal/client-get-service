package shop.household.client.get.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.household.client.get.entity.Client;
import shop.household.client.get.mapper.ClientMapper;
import shop.household.client.get.repository.ClientRepository;
import shop.household.model.ClientGetRequestDto;
import shop.household.model.ClientGetResponseDto;
import shop.household.model.ErrorDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService {
    @PersistenceContext
    private EntityManager entityManager;
    private final ClientRepository clientRepository;

    public ClientGetResponseDto getClients(ClientGetRequestDto clientDto) {
        List<Client> clients = new ArrayList<>();
        if (Objects.nonNull(clientDto.getId())) {
            return getById(clientDto.getId());
        } else if (Objects.nonNull(clientDto.getEmail())) {
            clientRepository.findByEmailIgnoreCase(clientDto.getEmail()).ifPresent(clients::add);
        } else {
            clients.addAll(getClientsByFilter(clientDto));
        }
        if (clients.isEmpty()) {
            return new ClientGetResponseDto()
                    .status(false)
                    .clients(null)
                    .error(new ErrorDto().code(404).message("Clients not found!"));
        }
        return new ClientGetResponseDto()
                .status(true)
                .clients(clients.stream().map(ClientMapper.INSTANCE::clientToClientDto).toList());
    }

    public List<Client> getClientsByFilter(ClientGetRequestDto clientDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        ClientFilter clientFilter = new ClientFilter(criteriaBuilder, root);
        List<Predicate> predicates = clientFilter.buildPredicates(clientDto);
        if (predicates.isEmpty()) {
            return new ArrayList<>();
        }
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public ClientGetResponseDto getById(Integer id) {
        var client = clientRepository.findById(id);
        if (client.isPresent()) {
            return new ClientGetResponseDto()
                    .status(true)
                    .clients(List.of(ClientMapper.INSTANCE.clientToClientDto(client.get())));
        }
        return new ClientGetResponseDto()
                .status(false)
                .clients(null)
                .error(new ErrorDto().code(404).message("Client with ID " + id + " not found!"));
    }
}
