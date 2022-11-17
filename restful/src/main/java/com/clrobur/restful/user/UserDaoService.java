package com.clrobur.restful.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    // 모든 유저 데이터를 저장할 List
    private static List<User> users = new ArrayList<>();
    
    // 개별 유저를 List에 저장
    static {
        users.add(new User(1, "First", new Date()));
        users.add(new User(2, "Second", new Date()));
        users.add(new User(3, "Third", new Date()));
    }
    
    // 저장된 유저의 수
    private static int userCount = 3;


    // --- 메서드 ---
    // 모든 유저 조회
    public List<User> findAll() {
        return users;
    }

    // 개별 유저 조회
    public User findOne(int id) {
        // 조회한 id가 있는 경우
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        // id가 없는 경우 null 반환
        return null;
    }

    // 유저 정보 저장
    public User save(User user) {
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    // 유저 정보 삭제
    public User deleteById(int id) {
        
        // for문 대신 iterator 사용
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {

            User user = iterator.next();
            // 조회한 id가 있을 경우 user 삭제
            if(user.getId() == id) {
                iterator.remove();
                return user; // 삭제 된 user 정보 반환
            }

        } // -반복문 종료
        return null; // id가 없을 경우 null 반환
    }

}
