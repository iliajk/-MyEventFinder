package com.MyEventFinder.model.specifications;

import com.MyEventFinder.model.entity.Entertainment;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class EntertainmentSpecification {
    public static Specification<Entertainment> hasType(Long typeId) {
        return ((root, query, criteriaBuilder) ->
                typeId == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("type").get("id"), typeId));
    }

    public static Specification<Entertainment> hasLocation(String location) {
        return ((root, query, criteriaBuilder) ->
                location == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("location").get("address"), "%" + location + "%"));
    }

    public static Specification<Entertainment> hasPriceLessThenOrEqual(BigDecimal price) {
        return ((root, query, criteriaBuilder) ->
                price == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
    }

    public static Specification<Entertainment> hasDeleted(Boolean deleted) {
        return ((root, query, criteriaBuilder) ->
                deleted == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("deleted"), deleted));
    }
}
