package com.me.prototype;

/**
 * 浅克隆，只能克隆基本类型，如果是引用类型，克隆的为应用类型的引用
 */
public interface IProtoType {
    IProtoType clone();
}
