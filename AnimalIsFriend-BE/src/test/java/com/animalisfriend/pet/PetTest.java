//package com.animalisfriend.pet;
//
//import com.animalisfriend.domain.Image.entity.ImageFile;
//import com.animalisfriend.domain.Image.repository.ImageFileRepository;
//import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
//import com.animalisfriend.domain.pets.dto.response.PetResponseDto;
//import com.animalisfriend.domain.pets.entity.PetCategory;
//import com.animalisfriend.domain.pets.entity.PetSize;
//import com.animalisfriend.domain.pets.entity.PetStatus;
//import com.animalisfriend.domain.pets.entity.Pets;
//import com.animalisfriend.domain.pets.repository.PetRepository;
//import com.animalisfriend.domain.pets.service.PetService;
//import com.animalisfriend.domain.users.entity.Users;
//import com.animalisfriend.domain.users.exception.UserNotFoundException;
//import com.animalisfriend.domain.users.repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import java.util.stream.LongStream;
//
//@SpringBootTest
//public class PetTest {
//
//    @Autowired
//    private PetRepository petRepository;
//    @Autowired
//    private PetService petService;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private ImageFileRepository imageFileRepository;
//
//    @Test
//    @DisplayName("dto 직접 조회 시간 테스트")
//    public void findPetTimeTestDto() {
//
//
//        PetRequestDto.findAllPetPagination dto = PetRequestDto.findAllPetPagination.builder()
//                .petId(null)
//                .petSize(null)
//                .petCategory(null)
//                .build();
//
//        long start = System.currentTimeMillis();
//        List<PetResponseDto.findAllPetDto> pets = petRepository.findAllPet(dto);
//        long end = System.currentTimeMillis();
//
//        System.out.println(end-start);
//    }
//
//    @Test
//    @DisplayName("엔티티 조회 후 dto 매핑 시간 테스트")
//    @Transactional
//    public void findPetTimeTestEntity() {
//
//        PetRequestDto.findAllPetPagination dto = PetRequestDto.findAllPetPagination.builder()
//                .petId(null)
//                .petSize(null)
//                .petCategory(null)
//                .build();
//
//        long start = System.currentTimeMillis();
//
//        List<PetResponseDto.findAllPetDto> pets = petRepository.findAllPetTest(dto)
//                .stream()
//                .map(
//                        pet ->
//                                PetResponseDto.findAllPetDto.from(
//                                        pet, pet.getUser(), imageFileRepository.findByPetId(pet.getPetId())
//                                )
//                )
//                .collect(Collectors.toList());
//
//        long end = System.currentTimeMillis();
//
//        System.out.println(end-start);
//    }
//
//
//
//    @Test
//    @DisplayName("반려동물에 임의 이미지파일 넣기")
//    public void registerImageByPet() {
//
//        LongStream.range(1, 100).forEach(i -> {
//            Pets pets = petRepository.findById(i).orElse(null);
//            Users users = userRepository.findById(pets.getUser().getId()).orElse(null);
//
//            imageFileRepository.save(ImageFile.of("", users, pets));
//            }
//        );
//    }
//
//    @Test
//    @DisplayName("반려동물이 잘 등록되는지 테스트")
//    public void registerPet() {
//
//        IntStream.range(1, 100).forEach(i -> {
//            String gender = i%2==0?"남자":"여자";
//            String breed = "비글";
//            PetCategory petCategory = PetCategory.DOG;
//            PetSize petSize = PetSize.SMALL;
//            PetStatus petStatus = PetStatus.NOT_ADOPTED;
//
//            if(i <= 20) {
//                breed="말티즈";
//            } else if(i <= 40) {
//                breed="먼치킨";
//                petCategory = PetCategory.CAT;
//                petSize = PetSize.X;
//            } else if(i <= 60) {
//                breed="비숑프리제";
//                petSize = PetSize.MIDDLE;
//                petStatus = PetStatus.ADOPTION_COMPLETE;
//            } else if(i <= 80) {
//                breed="푸들";
//                petSize = PetSize.SMALL;
//            } else {
//                breed="러시안 블루";
//                petCategory = PetCategory.CAT;
//                petSize = PetSize.X;
//            }
//
//            Long idx = (long) i;
//            Users users = userRepository.findById(idx)
//                    .orElseThrow(() -> new UserNotFoundException());
//
//            Pets pet = Pets.builder()
//                    .petName("pet"+i)
//                    .petGender(gender)
//                    .petBirth(String.valueOf(LocalDateTime.now()))
//                    .petAcquire(String.valueOf(LocalDateTime.now()))
//                    .petFeed("미파악")
//                    .petBreed(breed)
//                    .petCategory(petCategory)
//                    .petSize(petSize)
//                    .petStatus(petStatus)
//                    .petDescription("description"+i)
//                    .user(users)
//                    .build();
//
//            petRepository.save(pet);
//        });
//    }
//
//    @Test
//    @DisplayName("모든 반려동물이 잘 조회 되는지 테스트")
//    public void getAllPets() {
//
//        /*
//            given  - 준비
//         */
//        List<PetResponseDto.findAllPetDto> petsList = new ArrayList<>();
//        PetRequestDto.findAllPetPagination dto = PetRequestDto.findAllPetPagination.builder()
//                .petId(80L)
//                .build();
//
//        /*
//            when - 액션
//         */
//        petsList = petRepository.findAllPet(dto);
//        /*
//            then - 검증
//            페이징 테스트 포함 - 처음 20개는 최신순 petId, 그다음 페이징시 마지막 petId를 넘겨주어 다음 20개 가져오기
//         */
//        Assertions.assertThat(petsList.size()).isEqualTo(20);
//        Assertions.assertThat(petsList.get(0).getPetId()).isEqualTo(79);
//        Assertions.assertThat(petsList.get(19).getPetId()).isEqualTo(60);
//    }
//
//    @Test
//    @DisplayName("특정 반려동물이 조회 되는지 테스트")
//    public void getPet() {
//
//        //given
//        Long petId = 5L;
//        //when
//        Pets pet = petRepository.findById(petId).orElse(null);
//        //then
//        Assertions.assertThat(pet.getPetId()).isEqualTo(petId);
//    }
//
//    @Test
//    @DisplayName("반려동물 수정 테스트")
//    @Rollback(value = false)
//    @Transactional
//    public void petUpdate() {
//        //given
//        Long petId = 5L;
//
//        //when
//        PetRequestDto.petUpdateDto dto = new PetRequestDto.petUpdateDto(
//                "비프", "비숑프리제", "비프는 낯을 많이 가려요"
//                ,"사료는 시중에 파는 사료 아무거나 주시면 됩니다.", PetSize.MIDDLE
//                ,"" , PetStatus.NOT_ADOPTED);
//        Pets pet = petRepository.findById(petId).orElse(null);
//        pet.petUpdate(dto);
//
//        //then
//        Assertions.assertThat(pet.getPetName()).isEqualTo("비프");
//    }
//}
