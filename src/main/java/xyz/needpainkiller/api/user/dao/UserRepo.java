package xyz.needpainkiller.api.user.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.needpainkiller.api.user.model.UserEntity;

import java.util.List;

public interface UserRepo extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    List<UserEntity> findAll();

    List<UserEntity> findAllByIdIn(List<Long> idList);


    UserEntity findUserById(@NotNull Long userPk);

    List<UserEntity> findUserByUserId(String userId);
}