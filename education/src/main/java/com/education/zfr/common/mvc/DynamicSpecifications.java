package com.education.zfr.common.mvc;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

public class DynamicSpecifications {
    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz) {
        return new Specification<T>() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (!CollectionUtils.isEmpty(filters)) {
                    List<Predicate> addPredicates = Lists.newArrayList();
                    List<Predicate> orPredicates = Lists.newArrayList();
                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName,
                        // 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        if (SearchFilter.ConjunctionType.ADD.equals(filter.conjunctionType)) {
                            addPredicates.add(getPredicate(builder, filter, expression));
                        } else {
                            orPredicates.add(getPredicate(builder, filter, expression));
                        }
                    }

                    // 将所有条件用 and 联合起来
                    Predicate addPredicate = null;
                    if (!addPredicates.isEmpty()) {
                        addPredicate = builder.and(addPredicates.toArray(new Predicate[addPredicates.size()]));
                    }
                    Predicate orPredicate = null;
                    if (!orPredicates.isEmpty()) {
                        orPredicate = builder.and(orPredicates.toArray(new Predicate[orPredicates.size()]));
                    }
                    if (addPredicate != null) {
                        if (orPredicate != null) {
                            return builder.or(addPredicate, orPredicate);
                        }
                        return addPredicate;
                    }
                }

                return builder.conjunction();
            }

            /**
             * 
             * @param builder
             * @param predicates
             * @param filter
             * @param expression
             */
            private Predicate getPredicate(CriteriaBuilder builder, SearchFilter filter, Path expression) {
                switch (filter.operator) {
                    case EQ:
                        return builder.equal(expression, filter.value);
                    case NE:
                        return builder.notEqual(expression, filter.value);
                    case LIKE:
                        return builder.like(expression, "%" + filter.value + "%");
                    case NOTLIKE:
                        return builder.notLike(expression, "%" + filter.value + "%");
                    case GT:
                        return builder.greaterThan(expression, (Comparable) filter.value);
                    case LT:
                        return builder.lessThan(expression, (Comparable) filter.value);
                    case GTE:
                        return builder.greaterThanOrEqualTo(expression, (Comparable) filter.value);
                    case LTE:
                        return builder.lessThanOrEqualTo(expression, (Comparable) filter.value);
                    case IN:
                        String[] values = filter.value.toString().split(",");
                        return expression.in(values);
                    case NOTIN:
                        String[] values1 = filter.value.toString().split(",");
                        return builder.not(expression.in(values1));
                    case ISNULL:
                        return builder.isNull(expression);
                    case ISNOTNULL:
                        return builder.isNotNull(expression);
                    default:
                        return null;
                }
            }
        };
    }
}
