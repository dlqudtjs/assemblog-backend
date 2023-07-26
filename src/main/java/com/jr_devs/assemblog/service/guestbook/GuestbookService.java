package com.jr_devs.assemblog.service.guestbook;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.guestbook.GuestbookRequest;

public interface GuestbookService {

    ResponseDto createGuestbook(GuestbookRequest guestbookRequest, String token);

    ResponseDto deleteGuestbook(Long guestbookId, String password, String token);

    ResponseDto likeGuestbook(Long guestbookId);

    ResponseDto readGuestbookList();
}
