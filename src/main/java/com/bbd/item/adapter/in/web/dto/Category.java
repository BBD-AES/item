package com.bbd.item.adapter.in.web.dto;


import lombok.Getter;

@Getter
public enum Category {

    ENGINE_OIL("엔진/오일"),
    ENGINE_FILTER("엔진/필터"),
    IGNITION("점화"),
    BRAKE("제동"),
    POWERTRAIN("동력전달"),
    SUSPENSION_STEERING("현가/조향"),
    ELECTRICAL("전장"),
    COOLING("냉각"),
    AIR_CONDITIONING("공조/에어컨"),
    EXHAUST("배기"),
    TIRE_WHEEL("타이어/휠"),
    EXTERIOR("외장"),
    INTERIOR("내장"),
    WIPER_WASHER("와이퍼/워셔"),
    ACCESSORY_ETC("용품/기타");

    private final String label;

    Category(String label) {
        this.label = label;
    }

}
