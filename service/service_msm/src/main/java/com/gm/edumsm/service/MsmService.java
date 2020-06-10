package com.gm.edumsm.service;

import java.util.Map;

public interface MsmService {
    Boolean send(Map<String, Object> map, String phone);
}
