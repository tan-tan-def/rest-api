package com.assignment.asm03;

import com.assignment.asm03.common.DateTimeHelper;
import com.assignment.asm03.common.Field;
import com.assignment.asm03.entity.*;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final RoleService roleService;
    private final SpecializationsService specializationsService;
    private final HospitalService hospitalService;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final BasicConditionService basicConditionService;
    private final MedicalHistoryService medicalHistoryService;
    private final DoctorService doctorService;
    private final String emailAdmin = "thaiminh826345@gmail.com";
    private final String passwordAdmin = "test";

    @Override
    public void run(String... args) throws Exception {
        //Create new Role
        List<Role> role = roleService.findAll();
        if(role.isEmpty()){
            createRole();
        }

        //Create Admin
        try{
            User user = userService.findByEmail(emailAdmin);
            System.out.println("Tự động tạo Admin. Email: " + emailAdmin + " Password: test");
            if(user==null){
                createAdmin();
                createUser();
            }
        }catch (NotFoundException e){
            System.out.println("Tự động tạo Admin. Email: " + emailAdmin + " Password: test");
            createAdmin();
            createUser();
        }


        //Create new Specializations
        List<Specialization> specializations = specializationsService.findAll();
        if(specializations.isEmpty()){
            createSpecializations();
        }

        //Create new Hospital
        List<Hospital> hospitals = hospitalService.findAll();
        if(hospitals.isEmpty()){
            createHospital();
        }

        //Create Basic Condition
        List<BasicCondition> basicConditions = basicConditionService.findAll();
        if(basicConditions.isEmpty()){
            createBasicCondition();
        }

        //Create new 10 Doctor
        List<Doctor> doctors = doctorService.findAll();
        if(doctors.isEmpty()){
            createDoctor();
        }

        //Create medicalHistory
        List<MedicalHistory> medicalHistories = medicalHistoryService.findAll();
        if(medicalHistories.isEmpty()){
            createMedicalHistory();
        }

    }
    private void createRole(){
        Role userRoles = new Role("USER");
        Role adminRoles = new Role("ADMIN");
        Role doctorRoles = new Role("DOCTOR");
        roleService.save(userRoles);
        roleService.save(adminRoles);
        roleService.save(doctorRoles);
    }
    private void createSpecializations(){
        Specialization specialization1 = new Specialization("Nội khoa (Internal Medicine)", "Chuyên về chẩn đoán, điều trị và quản lý các bệnh lý nội tổ chức và các hệ thống cơ thể.", 1, 150000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization2 = new Specialization("Nhi khoa (Pediatrics)", "Chuyên về sức khỏe, phát triển và bệnh lý ở trẻ em và thanh thiếu niên.", 6, 200000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization3 = new Specialization("Da liễu (Dermatology)", "Chuyên về các bệnh lý của da, tóc và móng.", 2, 350000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization4 = new Specialization("Phẫu thuật (Surgery)", "Chuyên về phẫu thuật để điều trị các bệnh lý và thương tích.", 4, 600000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization5 = new Specialization("Nha khoa (Dentistry)", "Chuyên về điều trị và chăm sóc răng miệng, nướu và hàm.", 2, 100000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization6 = new Specialization("Sản phụ khoa (Obstetrics and Gynecology)", "Chuyên về chăm sóc phụ nữ mang thai, sinh nở và các bệnh phụ khoa.", 2, 250000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization7 = new Specialization("Tai mũi họng (Otorhinolaryngology)", "Chuyên về các bệnh lý của tai, mũi và họng.", 5, 130000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization8 = new Specialization("Tiểu đường - Chuyên khoa nội tiết (Endocrinology)", "Chuyên về tuyến nội tiết, hormon và các bệnh lý liên quan.", 8, 410000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization9 = new Specialization("Dinh dưỡng - Dinh dưỡng học (Nutrition - Dietetics)", "Chuyên về dinh dưỡng và chế độ ăn uống đúng cách để duy trì sức khỏe.", 7, 100000000,DateTimeHelper.getCurrentDateTime());
        Specialization specialization10 = new Specialization("Y học cổ truyền (Traditional Medicine)", "Chuyên về sử dụng các phương pháp truyền thống để điều trị và duy trì sức khỏe.", 10, 550000,DateTimeHelper.getCurrentDateTime());
        specializationsService.save(specialization1);
        specializationsService.save(specialization2);
        specializationsService.save(specialization3);
        specializationsService.save(specialization4);
        specializationsService.save(specialization5);
        specializationsService.save(specialization6);
        specializationsService.save(specialization7);
        specializationsService.save(specialization8);
        specializationsService.save(specialization9);
        specializationsService.save(specialization10);
    }
    private void createHospital(){
        Hospital hospital1 = new Hospital("Bệnh viện Bạch Mai", "78 Đường Giải Phóng, Đồng Tâm, Hai Bà Trưng, Hà Nội", "024 3869 3736", 3, "Bệnh viện đa khoa lớn nhất tại Hà Nội, cung cấp dịch vụ y tế đa dạng và chất lượng", DateTimeHelper.getCurrentDateTime());
        Hospital hospital2 = new Hospital("Bệnh viện Chợ Rẫy", "201B Nguyễn Chí Thanh, Phường 12, Quận 5, Hồ Chí Minh", "028 3855 4137", 5, "Bệnh viện đa khoa hàng đầu tại TP.HCM, trung tâm y tế uy tín tại Việt Nam", DateTimeHelper.getCurrentDateTime());
        Hospital hospital3 = new Hospital("Bệnh viện Việt Đức", "40 Tràng Thi, Hàng Trống, Hoàn Kiếm, Hà Nội", "024 3736 5142", 2, "Bệnh viện uy tín với đội ngũ y bác sĩ lành nghề và trang thiết bị hiện đại", DateTimeHelper.getCurrentDateTime());
        Hospital hospital4 = new Hospital("Bệnh viện Đại học Y Dược TP.HCM", "241 Phạm Ngọc Thạch, Phường 6, Quận 3, Hồ Chí Minh", "028 3836 8625", 1, "Trung tâm y tế hàng đầu chuyên về đào tạo, nghiên cứu và điều trị y khoa", DateTimeHelper.getCurrentDateTime());
        Hospital hospital5 = new Hospital("Bệnh viện 108", "108 Nguyễn Du, Phường 7, Quận 5, Hồ Chí Minh", "028 3858 2368", 0, "Bệnh viện trung ương lớn với dịch vụ y tế chất lượng và đội ngũ y bác sĩ giàu kinh nghiệm", DateTimeHelper.getCurrentDateTime());
        Hospital hospital6 = new Hospital("Bệnh viện Quốc Tế Vinmec", "458 Minh Khai, Vĩnh Tuy, Hai Bà Trưng, Hà Nội", "024 3974 3556", 7, "Bệnh viện đa khoa cao cấp với tiêu chuẩn quốc tế và dịch vụ y tế hiện đại", DateTimeHelper.getCurrentDateTime());
        Hospital hospital7 = new Hospital("Bệnh viện FV", "6 Nguyễn Lương Bằng, Nam Sài Gòn, Quận 7, Hồ Chí Minh", "028 5411 3333", 6, "Bệnh viện Quốc tế FV với đội ngũ chuyên gia hàng đầu và cơ sở vật chất hiện đại", DateTimeHelper.getCurrentDateTime());
        Hospital hospital8 = new Hospital("Bệnh viện French - Việt Nam", "1 Phương Mai, Đống Đa, Hà Nội", "024 3573 8339", 10, "Bệnh viện Pháp - Việt Nam với phong cách y tế chuyên nghiệp và phục vụ chu đáo", DateTimeHelper.getCurrentDateTime());
        Hospital hospital9 = new Hospital("Bệnh viện Nhi Đồng 1", "14 Lý Tự Trọng, Bến Thành, Quận 1, Hồ Chí Minh", "028 3823 3588", 8, "Bệnh viện chuyên về y tế trẻ em và là một trong những cơ sở y tế hàng đầu tại Việt Nam", DateTimeHelper.getCurrentDateTime());
        Hospital hospital10 = new Hospital("Bệnh viện Hữu Nghị Việt Đức", "284 Bà Triệu, Lê Đại Hành, Hai Bà Trưng, Hà Nội", "024 3974 3556", 5, "Bệnh viện đa khoa chất lượng cao với dịch vụ y tế đa dạng và chuyên nghiệp", DateTimeHelper.getCurrentDateTime());
        hospitalService.save(hospital1);
        hospitalService.save(hospital2);
        hospitalService.save(hospital3);
        hospitalService.save(hospital4);
        hospitalService.save(hospital5);
        hospitalService.save(hospital6);
        hospitalService.save(hospital7);
        hospitalService.save(hospital8);
        hospitalService.save(hospital9);
        hospitalService.save(hospital10);
    }
    private void createAdmin(){
        Role roleAdmin = roleService.findById(Field.ROLE_ADMIN);
        User user = new User("Hồ Thái Minh","Male",emailAdmin,"0923156743","Hồ Chí Minh",encoder.encode(passwordAdmin),roleAdmin,true);
        userService.save(user);
    }
    private void createBasicCondition(){
        BasicCondition basicCondition1 = new BasicCondition("Tăng huyết áp", "Tình trạng huyết áp liên tục cao hơn mức bình thường.", "Thay đổi lối sống và thuốc hạ huyết áp.", "Trung bình");
        BasicCondition basicCondition2 = new BasicCondition("Tiểu đường loại 2", "Tình trạng mãn tính ảnh hưởng đến cách cơ thể xử lý đường huyết.", "Chế độ ăn uống, tập thể dục, và thuốc.", "Nặng");
        BasicCondition basicCondition3 = new BasicCondition("Hen suyễn", "Tình trạng đường thở bị viêm và hẹp, gây ra sự sản xuất chất nhầy dư thừa.", "Thuốc corticosteroid hít và thuốc giãn phế quản.", "Nhẹ");
        BasicCondition basicCondition4 = new BasicCondition("Bệnh phổi tắc nghẽn mãn tính (COPD)", "Nhóm các bệnh phổi tiến triển gây tắc nghẽn đường thở và khó thở.", "Ngừng hút thuốc, thuốc hít, và liệu pháp oxy.", "Nặng");
        BasicCondition basicCondition5 = new BasicCondition("Viêm khớp dạng thấp", "Bệnh tự miễn chủ yếu ảnh hưởng đến các khớp, gây viêm và đau.", "Thuốc chống viêm và giảm đau.", "Trung bình");
        BasicCondition basicCondition6 = new BasicCondition("Cường giáp", "Tình trạng tuyến giáp hoạt động quá mức và sản xuất quá nhiều hormone giáp.", "Thuốc kháng giáp, i-ốt phóng xạ, hoặc phẫu thuật.", "Trung bình");
        BasicCondition basicCondition7 = new BasicCondition("Bệnh trào ngược dạ dày thực quản (GERD)", "Rối loạn tiêu hóa khiến axit dạ dày thường xuyên chảy ngược vào thực quản.", "Thuốc chống axit và thay đổi lối sống.", "Nhẹ");
        BasicCondition basicCondition8 = new BasicCondition("Vảy nến", "Tình trạng da gây ra các vết đỏ, ngứa và vảy.", "Điều trị tại chỗ, liệu pháp ánh sáng, và thuốc toàn thân.", "Trung bình");
        BasicCondition basicCondition9 = new BasicCondition("Loãng xương", "Tình trạng đặc trưng bởi xương yếu và dễ gãy, tăng nguy cơ gãy xương.", "Bổ sung canxi và vitamin D, và thuốc.", "Nặng");
        BasicCondition basicCondition10 = new BasicCondition("Đau nửa đầu", "Một loại đau đầu thường kèm theo buồn nôn, nôn mửa, và nhạy cảm với ánh sáng.", "Thuốc giảm đau và điều chỉnh lối sống.", "Trung bình");
        basicConditionService.save(basicCondition1);
        basicConditionService.save(basicCondition2);
        basicConditionService.save(basicCondition3);
        basicConditionService.save(basicCondition4);
        basicConditionService.save(basicCondition5);
        basicConditionService.save(basicCondition6);
        basicConditionService.save(basicCondition7);
        basicConditionService.save(basicCondition8);
        basicConditionService.save(basicCondition9);
        basicConditionService.save(basicCondition10);
    }
    private void createMedicalHistory(){
        MedicalHistory history1 = new MedicalHistory("Cảm lạnh thông thường", "Nghỉ ngơi và uống nhiều nước", "Khuyến khích uống thuốc giảm đau nếu cần.","18/05/2023");
        MedicalHistory history2 = new MedicalHistory("Viêm phổi", "Kháng sinh và thuốc giảm ho", "Yêu cầu theo dõi tình trạng hô hấp.","21/04/2023");
        MedicalHistory history3 = new MedicalHistory("Đau dạ dày cấp", "Thuốc ức chế acid dạ dày", "Tránh thực phẩm cay và chua.","13/09/2024");
        MedicalHistory history4 = new MedicalHistory("Tiểu đường type 2", "Kiểm soát đường huyết bằng thuốc và chế độ ăn uống", "Cần theo dõi đường huyết định kỳ.","16/02/2024");
        MedicalHistory history5 = new MedicalHistory("Tăng huyết áp", "Thuốc hạ huyết áp và chế độ ăn giảm muối", "Khuyến khích tập thể dục đều đặn.","28/04/2024");
        MedicalHistory history6 = new MedicalHistory("Viêm khớp gối", "Thuốc giảm đau và vật lý trị liệu", "Tránh các hoạt động gây áp lực lên khớp gối.","19/03/2024");
        MedicalHistory history7 = new MedicalHistory("Mẫn cảm thuốc kháng sinh", "Ngừng thuốc và sử dụng thuốc thay thế", "Theo dõi phản ứng dị ứng và điều chỉnh điều trị nếu cần.","11/08/2024");
        MedicalHistory history8 = new MedicalHistory("Hen suyễn", "Sử dụng thuốc giãn phế quản và thuốc chống viêm", "Tránh các tác nhân kích thích như bụi và khói.","25/11/2023");
        MedicalHistory history9 = new MedicalHistory("Hội chứng ruột kích thích", "Thay đổi chế độ ăn uống và dùng thuốc điều trị triệu chứng", "Khuyến khích ghi lại thực phẩm gây triệu chứng.","17/06/2023");
        MedicalHistory history10 = new MedicalHistory("Chứng mất ngủ", "Thay đổi lối sống và dùng thuốc ngủ nếu cần", "Tạo thói quen ngủ đều đặn và thư giãn trước khi ngủ.","05/06/2022");
        MedicalHistory history11 = new MedicalHistory("Khỏe mạnh", "Không cần điều trị gì", "Sức khỏe rất tốt","13/09/2024");

        medicalHistoryService.save(history1);
        medicalHistoryService.save(history2);
        medicalHistoryService.save(history3);
        medicalHistoryService.save(history4);
        medicalHistoryService.save(history5);
        medicalHistoryService.save(history6);
        medicalHistoryService.save(history7);
        medicalHistoryService.save(history8);
        medicalHistoryService.save(history9);
        medicalHistoryService.save(history10);
        medicalHistoryService.save(history11);
    }
    public void createUser(){
        Role role = roleService.findById(Field.ROLE_DOCTOR);
        Role roleUser = roleService.findById(Field.ROLE_USER);
        User user0 = new User("tantan", "Nam","tanmnfx20821@funix.edu.vn","0985136745","Hồ Chí Minh", encoder.encode("test"), roleUser,true);
        User user1 = new User("Đỗ Văn Lam", "Nam", "vanlam1995@gmail.com", "0953167854", "Hà Nội", encoder.encode("test"), role, true);
        User user2 = new User("Trần Thị Hoa", "Nữ", "tranhoa1990@gmail.com", "0967854321", "Hồ Chí Minh", encoder.encode("test"), role, true);
        User user3 = new User("Nguyễn Văn An", "Nam", "nguyena@gmail.com", "0976543210", "Đà Nẵng", encoder.encode("test"), role, true);
        User user4 = new User("Lê Thị Bích", "Nữ", "lebi@gmail.com", "0987654321", "Hải Phòng", encoder.encode("test"), role, true);
        User user5 = new User("Phạm Văn Quang", "Nam", "phamquang@gmail.com", "0998765432", "Cần Thơ", encoder.encode("test"), role, true);
        User user6 = new User("Hồ Thị Linh", "Nữ", "hotlinh@gmail.com", "0912345678", "Hạ Long", encoder.encode("test"), role, true);
        User user7 = new User("Bùi Văn Sơn", "Nam", "buison@gmail.com", "0923456789", "Nha Trang", encoder.encode("test"), role, true);
        User user8 = new User("Cao Thị Mai", "Nữ", "caomai@gmail.com", "0934567890", "Quảng Ninh", encoder.encode("test"), role, true);
        User user9 = new User("Vũ Văn Hòa", "Nam", "vuh@gmail.com", "0945678901", "Vinh", encoder.encode("test"), role, true);
        User user10 = new User("Nguyễn Thị Thu", "Nữ", "nguyenthi@gmail.com", "0956789012", "Bến Tre", encoder.encode("test"), role, true);
        userService.save(user0);
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);
        userService.save(user5);
        userService.save(user6);
        userService.save(user7);
        userService.save(user8);
        userService.save(user9);
        userService.save(user10);
    }
    public void createDoctor(){
        List<Specialization> specializations = specializationsService.findAll();
        List<Hospital> hospitals = hospitalService.findAll();
        List<User> users = userService.findAll();

        Doctor doctor1 = new Doctor("Bác sĩ giàu kinh nghiệm", "Được dào tạo cử nhân chuyên sâu răng hàm mặt","Chữa lành răng cho rất nhiều bệnh nhân",hospitals.get(0),specializations.get(0),users.get(2));
        Doctor doctor2 = new Doctor("Bác sĩ có nhiều năm kinh nghiệm trong việc điều trị các bệnh lý ở trẻ em, cung cấp chăm sóc tận tâm cho các bệnh nhân nhỏ tuổi.",
                "Tốt nghiệp Đại học Y dược và hoàn thành chương trình đào tạo chuyên sâu về nhi khoa.",
                "Đã điều trị thành công hàng ngàn bệnh nhi và nhận nhiều giải thưởng trong lĩnh vực nhi khoa.",hospitals.get(1),specializations.get(1),users.get(3));
        Doctor doctor3 = new Doctor("Bác sĩ chuyên về điều trị và quản lý các bệnh lý tim mạch và huyết áp, cam kết mang lại sức khỏe tốt nhất cho bệnh nhân.",
                "Tốt nghiệp Đại học Y Hà Nội và hoàn thành chương trình đào tạo chuyên sâu về tim mạch tại Hoa Kỳ.",
                "Đã thực hiện hàng trăm ca phẫu thuật tim mạch thành công và nhận nhiều bằng khen trong lĩnh vực này.",hospitals.get(2),specializations.get(2),users.get(4));
        Doctor doctor4 = new Doctor("Bác sĩ chuyên điều trị các vấn đề về da, từ các bệnh lý phổ biến đến các vấn đề da liễu phức tạp, giúp bệnh nhân cải thiện sức khỏe làn da.",
                "Tốt nghiệp Đại học Y dược và hoàn thành chương trình đào tạo về da liễu tại Pháp.",
                "Đã công bố nhiều nghiên cứu về bệnh lý da và nhận giải thưởng về nghiên cứu da liễu.",hospitals.get(3),specializations.get(3),users.get(5));
        Doctor doctor5 = new Doctor("Bác sĩ chuyên về sức khỏe phụ nữ và các vấn đề sinh sản, cung cấp dịch vụ chăm sóc toàn diện từ tuổi dậy thì đến tuổi mãn kinh.",
                "Tốt nghiệp Đại học Y dược và hoàn thành chương trình đào tạo về phụ khoa tại Úc.",
                "Đã điều trị thành công nhiều ca sinh sản khó và nhận giải thưởng về chăm sóc phụ khoa.",hospitals.get(4),specializations.get(4),users.get(6));
        Doctor doctor6 = new Doctor("Bác sĩ chuyên về chăm sóc và điều trị các bệnh lý liên quan đến mắt, giúp bệnh nhân duy trì hoặc cải thiện thị lực.",
                "Tốt nghiệp Đại học Y và hoàn thành đào tạo chuyên sâu về nhãn khoa tại Nhật Bản.",
                "Đã thực hiện nhiều ca phẫu thuật mắt thành công và được trao tặng nhiều giải thưởng về nhãn khoa.",hospitals.get(5),specializations.get(5),users.get(7));
        Doctor doctor7 = new Doctor("Bác sĩ chuyên thực hiện các phẫu thuật để điều trị các bệnh lý và chấn thương, cam kết mang lại kết quả tốt nhất cho bệnh nhân.",
                "Tốt nghiệp Đại học Y và hoàn thành đào tạo về ngoại khoa tại Đức.",
                "Đã thực hiện hàng ngàn ca phẫu thuật và nhận nhiều giải thưởng về kỹ thuật phẫu thuật.",hospitals.get(6),specializations.get(6),users.get(8));
        Doctor doctor8 = new Doctor("Bác sĩ chuyên chẩn đoán và điều trị các bệnh lý nội khoa, tập trung vào việc quản lý và điều trị các bệnh không cần phẫu thuật.",
                "Tốt nghiệp Đại học Y và hoàn thành chương trình đào tạo về nội khoa tại Canada.",
                "Được vinh danh với nhiều giải thưởng về chẩn đoán và điều trị nội khoa.",hospitals.get(7),specializations.get(7),users.get(9));
        Doctor doctor9 = new Doctor("Bác sĩ chuyên điều trị các chấn thương và bệnh lý liên quan đến hệ cơ xương, giúp bệnh nhân phục hồi chức năng và giảm đau.",
                "Tốt nghiệp Đại học Y và hoàn thành chương trình đào tạo về chấn thương chỉnh hình tại Hàn Quốc.",
                "Đã điều trị thành công nhiều ca chấn thương nghiêm trọng và nhận giải thưởng trong lĩnh vực chỉnh hình.",hospitals.get(8),specializations.get(8),users.get(10));
        Doctor doctor10 = new Doctor("Bác sĩ chuyên sử dụng các phương pháp y học cổ truyền để điều trị và hỗ trợ sức khỏe bệnh nhân, mang lại sự cân bằng cho cơ thể.",
                "Tốt nghiệp Đại học Y và hoàn thành chương trình đào tạo về y học cổ truyền tại Trung Quốc.",
                "Được công nhận với nhiều giải thưởng về y học cổ truyền và có nhiều bài nghiên cứu được xuất bản.",hospitals.get(9),specializations.get(9),users.get(11));
        doctorService.save(doctor1);
        doctorService.save(doctor2);
        doctorService.save(doctor3);
        doctorService.save(doctor4);
        doctorService.save(doctor5);
        doctorService.save(doctor6);
        doctorService.save(doctor7);
        doctorService.save(doctor8);
        doctorService.save(doctor9);
        doctorService.save(doctor10);
    }
}
