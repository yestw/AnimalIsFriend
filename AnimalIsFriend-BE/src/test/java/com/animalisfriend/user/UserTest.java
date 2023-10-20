//package com.animalisfriend.user;
//
//import com.animalisfriend.domain.users.dto.UserUpdateRequestDto;
//import com.animalisfriend.domain.users.entity.Provider;
//import com.animalisfriend.domain.users.entity.UserRole;
//import com.animalisfriend.domain.users.entity.Users;
//import com.animalisfriend.domain.users.repository.UserRepository;
//import com.animalisfriend.domain.users.service.UserService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class UserTest {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;
//
//    @Test
//    @DisplayName("유저가 잘 만들어지는지 테스트")
//    public void registerTest() {
//
//        /*
//            given  - 준비
//         */
//        Users users = Users.builder()
//                .userName("userName")
//                .userHp("01011112222")
//                .userAddress("Address")
//                .userNickname("userNickname")
//                .userGender("남자")
//                .provider(Provider.GOOGLE)
//                .providerId("1231312")
//                .role(UserRole.USER)
//                .build();
//
//
//        /*
//            when - 액션,  then - 검증
//         */
//        Assertions.assertThat(users.getUserName()).isEqualTo("userName");
//        Assertions.assertThat(users.getUserHp()).isEqualTo("01011112222");
//    }
//
//    @Test
//    @DisplayName("유저 업데이트 테스트")
//    public void userUpdate() {
//        /*
//            given  - 준비
//         */
//        Users users = Users.builder()
//                .userName("userName")
//                .userHp("01011112222")
//                .userAddress("Address")
//                .userNickname("userNickname")
//                .userGender("남자")
//                .provider(Provider.GOOGLE)
//                .providerId("1231312")
//                .role(UserRole.USER)
//                .build();
//        /*
//            when - 액션,
//         */
//        UserUpdateRequestDto dto = UserUpdateRequestDto.builder()
//                .userName("수정")
//                .build();
//
//        users.userInfoUpdate(dto);
//        /*
//            then - 검증
//         */
//        Assertions.assertThat(users.getUserName()).isEqualTo("수정");
//    }
//
//    @Test
//    @DisplayName("유저 등록 테스트 - DB")
//    public void userRegister() {
//
//        IntStream.range(1, 100).forEach(i -> {
//            String hp = String.format("0101234%04d", i);
//            String userGender = i%2==0?"남자":"여자";
//            Provider provider = i%2==0?Provider.KAKAO:Provider.GOOGLE;
//
//            Users users = Users.builder()
//                    .userName("u"+i)
//                    .userHp(hp)
//                    .userAddress("Address"+i)
//                    .userNickname("userNickname"+i)
//                    .userGender(userGender)
//                    .provider(provider)
//                    .providerId(String.valueOf(i))
//                    .role(UserRole.USER)
//                    .build();
//
//            userRepository.save(users);
//        });
//    }
//
//    @Test
//    @DisplayName("유저 삭제 테스트")
//    public void userDelete() {
//        /*
//            given  - 준비
//         */
//        Users users = Users.builder()
//                .userName("user")
//                .userHp("01011112222")
//                .userAddress("Address")
//                .userNickname("userNickname")
//                .userGender("남자")
//                .provider(Provider.GOOGLE)
//                .providerId("1231312")
//                .role(UserRole.USER)
//                .build();
//        /*
//            when - 액션,
//         */
//        userRepository.save(users);
//        userService.withdrawalMembership(users.getId());
//
//        /*
//            then - 검증
//         */
//        Assertions.assertThat(users.getUserName()).isNull();
//    }
//}
