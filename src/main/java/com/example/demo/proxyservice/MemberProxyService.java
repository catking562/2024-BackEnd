package com.example.demo.proxyservice;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.entity.Article;
import com.example.demo.entity.Member;
import com.example.demo.exception.ExceptionType;
import com.example.demo.exception.HTTPApiException;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Service
public class MemberProxyService {

    private final MemberService memberservice;

    public MemberProxyService(MemberService memberservice) {
        this.memberservice = memberservice;
    }

    public List<MemberResponse> getAll() {
        return memberservice.getAll();
    }

    public MemberResponse getById(Long id) {
        return memberservice.getById(id);
    }

    public MemberResponse create(MemberCreateRequest request) throws HTTPApiException {
        if(request.name()==null) throw new HTTPApiException(ExceptionType.Member_NotNullName);
        if(request.email()==null) throw new HTTPApiException(ExceptionType.Member_NotNullEmail);
        if(request.password()==null) throw new HTTPApiException(ExceptionType.Member_NotNullPassWord);
        if(memberservice.isExistEmail(0L, request.email())) throw new HTTPApiException(ExceptionType.Member_OverLapEmail);
        return memberservice.create(request);
    }

    public MemberResponse update(Long id, MemberUpdateRequest request) throws HTTPApiException {
        if(request.name()==null) throw new HTTPApiException(ExceptionType.Member_NotNullName);
        if(request.email()==null) throw new HTTPApiException(ExceptionType.Member_NotNullEmail);
        if(memberservice.isExistEmail(id, request.email())) throw new HTTPApiException(ExceptionType.Member_OverLapEmail);
        return memberservice.update(id, request);
    }

    public void delete(Long id) throws HTTPApiException {
        if(!memberservice.isExistUser(id)) throw new HTTPApiException(ExceptionType.Member_IsNull);
        try{
            memberservice.delete(id);
        }catch(Exception e) {
            throw new HTTPApiException(ExceptionType.Member_CantDelete);
        }
    }
}
