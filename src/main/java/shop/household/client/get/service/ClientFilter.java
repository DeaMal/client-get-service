package shop.household.client.get.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import shop.household.client.get.entity.Client;
import shop.household.model.ClientGetRequestDto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientFilter {
    private final CriteriaBuilder criteriaBuilder;
    private final Root<Client> root;
    private final List<Predicate> predicates;

    public ClientFilter(CriteriaBuilder criteriaBuilder, Root<Client> root) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
        this.predicates = new ArrayList<>();
    }

    public List<Predicate> buildPredicates(ClientGetRequestDto clientDto) {
        addLikePredicate("firstname", clientDto.getFirstname());
        addLikePredicate("lastname", clientDto.getLastname());
        addLikePredicate("phone", clientDto.getPhone());
        addLikePredicate("phoneExtra", clientDto.getPhoneExtra());
        addLikePredicate("address", clientDto.getAddress());
        if (Objects.nonNull(clientDto.getCreateAt())) {
            addDateRangePredicate("createAt", clientDto.getCreateAt().getBegin(), clientDto.getCreateAt().getEnd(), new Timestamp(0), new Timestamp(System.currentTimeMillis()));
        }
        if (Objects.nonNull(clientDto.getUpdatedAt())) {
            addDateRangePredicate("updatedAt", clientDto.getUpdatedAt().getBegin(), clientDto.getUpdatedAt().getEnd(), new Timestamp(0), new Timestamp(System.currentTimeMillis()));
        }
        addLikePredicate("notes", clientDto.getNotes());
        if (Objects.nonNull(clientDto.getLastOrderDate())) {
            addDateRangePredicate("lastOrderDate", clientDto.getLastOrderDate().getBegin(), clientDto.getLastOrderDate().getEnd(), new Date(0), new Date(System.currentTimeMillis()));
        }
        if (Objects.nonNull(clientDto.getTotalOrders())) {
            addRangePredicate("totalOrders", clientDto.getTotalOrders().getMin(), clientDto.getTotalOrders().getMax(), 0);
        }
        if (Objects.nonNull(clientDto.getTotalSpent())) {
            addRangePredicate("totalSpent", clientDto.getTotalSpent().getMin(), clientDto.getTotalSpent().getMax(), BigDecimal.ZERO);
        }
        return predicates;
    }

    private void addLikePredicate(String fieldName, String value) {
        if (Objects.nonNull(value)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%"));
        }
    }

    private <T extends Comparable<T>> void addDateRangePredicate(String fieldName, T begin, T end, T defaultBegin, T defaultEnd) {
        if (Objects.isNull(begin)) {
            begin = defaultBegin;
        }
        if (Objects.isNull(end)) {
            end = defaultEnd;
        }
        predicates.add(criteriaBuilder.between(root.get(fieldName), begin, end));
    }

    private <T extends Comparable<T>> void addRangePredicate(String fieldName, T min, T max, T defaultMin) {
        if (Objects.isNull(min)) {
            min = defaultMin;
        }
        if (Objects.isNull(max)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), min));
        } else {
            predicates.add(criteriaBuilder.between(root.get(fieldName), min, max));
        }
    }
}
