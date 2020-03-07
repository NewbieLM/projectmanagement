package com.maksym.projectmanagement;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.model.User;
import com.maksym.projectmanagement.repository.hibernate.HibernateSkillRepository;
import com.maksym.projectmanagement.repository.hibernate.HibernateUserRepository;
import com.maksym.projectmanagement.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Initializer initializer = new Initializer();
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository();
     /*   User user = new User("KRAB");
        user.addSkill(new Skill(1,"Java"));
        user.addSkill(new Skill(4,"JavaScript"));
      // hibernateUserRepository.save(user);
 user = hibernateUserRepository.get(12);
        System.out.println(user);
        user.setName("KU KU");
        user.addSkill(new Skill());
        hibernateUserRepository.update(user);
*/
        System.out.println("======================");

        System.out.println(hibernateUserRepository.getAll());

        /*List<User> users = hibernateUserRepository.getAll();

        for(User u : users){
            System.out.println(u);
        }

        /*HibernateSkillRepository hibernateSkillRepository = new HibernateSkillRepository();
        Skill php = new Skill(8, "ABR");
        hibernateSkillRepository.saveNewSkill(php);

        System.out.println(php);


        List<Skill> skills = hibernateSkillRepository.getAll();

        for (Skill skill : skills) {
            System.out.println(skill);
        }
*/

    }
}
