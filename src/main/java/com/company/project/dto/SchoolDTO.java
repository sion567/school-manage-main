package com.company.project.dto;


import com.company.project.core.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public record SchoolDTO (String name, String email, String phoneNumber, String province, String street) implements BaseDTO {
    public static SchoolDTO from(String name, String email, String phoneNumber, String province, String street) {
        return new SchoolDTO(name, email, phoneNumber, province, street);
    }
}


//public record UserDTO(Long id, String name, String email, List<OrderDTO> orders) {
//    public static UserDTO from(User user, List<Order> orders) {
//        List<OrderDTO> orderDTOs = orders.stream()
//                .map(order -> new OrderDTO(order.getId(), order.getOrderNumber(), order.getAmount()))
//                .collect(Collectors.toList());
//        return new UserDTO(user.getId(), user.getName(), user.getEmail(), orderDTOs);
//    }
//}
//return UserDTO.from(user, orders);


//mapper.createTypeMap(User.class, UserDTO.class)
//            .setProvider(context -> {
//                User source = (User) context.getSource();
//                return new UserDTO(source.getId(), source.getName(), source.getEmail());
//        });