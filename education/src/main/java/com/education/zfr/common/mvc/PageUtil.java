package com.education.zfr.common.mvc;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageUtil {
    /**
     * 创建分页请求，并按照指定的顺序和属性进行排序 单个字段排序，比如direction为"desc",oderBy为"id",会按照id降序
     * 
     * @param pageNumber
     * @param pageSize
     * @param direction 排序方向，可选值包括："asc","desc"
     * @param orderBy
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, String direction, String orderBy) {
        Sort sort = null;
        if ("desc".equalsIgnoreCase(direction)) {
            sort = new Sort(Direction.DESC, orderBy);
        } else {
            sort = new Sort(Direction.ASC, orderBy);
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 创建分页请求，并按照指定的顺序和属性进行排序 排序方向相同的多个字段排序，比如direction为"desc",oderBy为{"id","age"},会按照id降序,然后age降序
     * 
     * @param pageNumber
     * @param pageSize
     * @param direction 排序方向，可选值包括："asc","desc"
     * @param orderBys
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, String direction, String[] orderBys) {
        Sort sort = null;
        if ("desc".equalsIgnoreCase(direction)) {
            sort = new Sort(Direction.DESC, orderBys);
        } else {
            sort = new Sort(Direction.ASC, orderBys);
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 创建分页请求，并按照指定的顺序和属性进行排序 排序方向相同的多个字段排序，比如direction为"desc",oderBy为{"id","age"},会按照id降序,然后age降序
     * 
     * @param pageNumber
     * @param pageSize
     * @param direction 排序方向，可选值包括："asc","desc"
     * @param orderBys
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, String direction, List<String> orderBys) {
        Sort sort = null;
        if ("desc".equalsIgnoreCase(direction)) {
            sort = new Sort(Direction.DESC, orderBys);
        } else {
            sort = new Sort(Direction.ASC, orderBys);
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    public static PageRequest buildPageRequest(int pageNumber, int pageSize, Map<String, Direction> sorts) {
        Sort sort = null;
        if (!CollectionUtils.isEmpty(sorts)) {
            List<Order> orders = new ArrayList<Order>();
            for (String orderBy : sorts.keySet()) {
                orders.add(new Order(null == sorts.get(orderBy) ? Direction.ASC : sorts.get(orderBy), orderBy));

            }
            sort = new Sort(orders);
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 创建分页请求.
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize) {
        Sort sort = null;
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 创建分页请求，并按照指定的顺序和属性进行排序
     * 排序方向不同的多个字段排序，例如directions中包含排序方向{"asc","desc","asc"},orderBys中包含需要排序的字段{"id","age","name"},
     * 则排序的效果为先按照id升序 ，再按age降序，再按name升序排列。directions和oderBys list大小要相同
     * 
     * @param pageNumber
     * @param pageSize
     * @param directions 排序方向，可选值包括："asc","desc"
     * @param orderBys 需要排序的字段
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, List<String> directions,
            List<String> orderBys) {
        Sort sort = null;
        if (directions != null && orderBys != null) {
            int directionsNo = directions.size();
            int fieldsNo = orderBys.size();
            if (directionsNo == fieldsNo) {
                for (int i = 0; i < directionsNo; i++) {
                    String direction = directions.get(i);
                    String orderBy = orderBys.get(i);
                    if (i == 0) {
                        if ("desc".equalsIgnoreCase(direction)) {
                            sort = new Sort(Direction.DESC, orderBy);
                        } else {
                            sort = new Sort(Direction.ASC, orderBy);
                        }
                    } else {
                        if ("desc".equalsIgnoreCase(direction)) {
                            sort.and(new Sort(Direction.DESC, orderBy));
                        } else {
                            sort.and(new Sort(Direction.ASC, orderBy));
                        }
                    }
                }
            }
        }

        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 创建动态查询条件组合.
     */
    @SuppressWarnings("unchecked")
    public static <T> Specification<T> buildSpecification(Map<String, Object> searchParams, Class<T> t) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<T> spec = (Specification<T>) DynamicSpecifications.bySearchFilter(filters.values(), t.getClass());
        return spec;
    }
}
