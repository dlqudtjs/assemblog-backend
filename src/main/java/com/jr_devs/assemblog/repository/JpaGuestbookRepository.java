package com.jr_devs.assemblog.repository;


import com.jr_devs.assemblog.model.guestbook.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaGuestbookRepository extends JpaRepository<Guestbook, Long> {

    Guestbook save(Guestbook guestbook);

    Optional<Guestbook> findById(Long id);

    void deleteById(Long id);

    int countAllByParentCommentId(Long parentCommentId);
}
