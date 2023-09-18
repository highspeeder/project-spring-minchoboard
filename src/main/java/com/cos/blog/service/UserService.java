package com.cos.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    @Transactional
    public void join(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);

        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        user.setIsOAuthUser(false);

        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        // 수정시에는 영속성 컨텍스트 User오브젝트를 영속화시키고, 영속화된 User오브젝트를 수정
        // select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });

        //OAuth로그인유저가 아닌 사람만 패스워드 수정가능
        if (persistance.getIsOAuthUser() == false) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);

            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }

        

        // 메서드가 종료할 때, 트랜잭션이 종료되고, DB에 커밋이 된다.
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
    }

    // //select할 때 트랜젝션 시작되고, 서비스 종료할 때 트랜젝션 종료
    // @Transactional(readOnly=true)
    // public User login(User user) {
    // return userRepository.findByUsernameAndPassword(user.getUsername(),
    // user.getPassword());
    // }
}
