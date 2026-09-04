package com.wallet_task.wallet_management.wallethere.user.repository;

import com.wallet_task.wallet_management.wallethere.user.enetity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
