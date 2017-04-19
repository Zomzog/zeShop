package bzh.zomzog.zeshop.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import bzh.zomzog.zeshop.domain.Authority;
import bzh.zomzog.zeshop.domain.User;
import bzh.zomzog.zeshop.service.dto.UserDTO;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    List<UserDTO> usersToUserDTOs(List<User> users);

    @Mappings({ //
            @Mapping(target = "createdBy", ignore = true), //
            @Mapping(target = "createdDate", ignore = true), //
            @Mapping(target = "lastModifiedBy", ignore = true), //
            @Mapping(target = "lastModifiedDate", ignore = true), //
            @Mapping(target = "activationKey", ignore = true), //
            @Mapping(target = "resetKey", ignore = true), //
            @Mapping(target = "resetDate", ignore = true), //
            @Mapping(target = "password", ignore = true),//
    })
    User userDTOToUser(UserDTO userDTO);

    List<User> userDTOsToUsers(List<UserDTO> userDTOs);

    default User userFromId(final Long id) {
        if (id == null) {
            return null;
        }
        final User user = new User();
        user.setId(id);
        return user;
    }

    default Set<String> stringsFromAuthorities(final Set<Authority> authorities) {
        return authorities.stream().map(Authority::getName).collect(Collectors.toSet());
    }

    default Set<Authority> authoritiesFromStrings(final Set<String> strings) {
        return strings.stream().map(string -> {
            final Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
}
