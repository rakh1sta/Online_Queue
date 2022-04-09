package uz.sh.online_queue.controller;


import uz.sh.online_queue.service.BaseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractController <S extends BaseService>{
    protected final S service;
    protected final String API = "/api";
    protected final String VERSION = "/v1";
    protected final String PATH = API + VERSION;

}
