/*
 * Copyright 2024 Steve Pan
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.steve.graphic;

import org.joml.Vector3f;

public class Material {

  private String id;
  private float Ns; // 反射指數
  private float Ni; // 折射率
  private float d; // 漸隱指數     1.0 - 0.0 (完全透明)
  private float Tr; // 材質透明度   1.0 - 0.0 (完全不透明)
  private Vector3f Tf; // 濾光折射率
  private int illum; // 光照糢型
  private Vector3f Ka; // 環境光
  private Vector3f Kd; // 散射光
  private Vector3f Ks; // 鏡面光
  private Vector3f Ke; // 發射光

  private Integer mapKa;
  private Integer mapKd;
  private Integer mapKs;
  private Integer mapRefl;

  public Material(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Material<%s>: \n", id));
    sb.append(String.format(" Ns: %f \n", Ns));
    sb.append(String.format(" Ni: %f \n", Ni));
    sb.append(String.format(" d: %f \n", d));
    sb.append(String.format(" Tr: %f \n", Tr));
    if (Tf != null) sb.append(String.format(" Tf: %s \n", Tf));
    sb.append(String.format(" illum: %d \n", illum));
    if (Ka != null) sb.append(String.format(" Ka: %s \n", Ka));
    if (Kd != null) sb.append(String.format(" Kd: %s \n", Kd));
    if (Ks != null) sb.append(String.format(" Ks: %s \n", Ks));
    if (Ke != null) sb.append(String.format(" Ke: %s \n", Ke));
    if (mapKa != null) sb.append(String.format(" mapKa: %s \n", mapKa));
    if (mapKd != null) sb.append(String.format(" mapKd: %s \n", mapKd));
    if (mapKs != null) sb.append(String.format(" mapKs: %s \n", mapKs));
    if (mapRefl != null) sb.append(String.format(" mapRefl: %s", mapRefl));
    return sb.toString();
  }

  public String getId() {
    return id;
  }

  public float getNs() {
    return Ns;
  }

  public void setNs(float Ns) {
    this.Ns = Ns;
  }

  public float getNi() {
    return Ni;
  }

  public void setNi(float Ni) {
    this.Ni = Ni;
  }

  public float getD() {
    return d;
  }

  public void setD(float d) {
    this.d = d;
  }

  public float getTr() {
    return Tr;
  }

  public void setTr(float Tr) {
    this.Tr = Tr;
  }

  public Vector3f getTf() {
    return Tf;
  }

  public void setTf(Vector3f Tf) {
    this.Tf = Tf;
  }

  public int getIllum() {
    return illum;
  }

  public void setIllum(int illum) {
    this.illum = illum;
  }

  public Vector3f getKa() {
    return Ka;
  }

  public void setKa(Vector3f Ka) {
    this.Ka = Ka;
  }

  public Vector3f getKd() {
    return Kd;
  }

  public void setKd(Vector3f Kd) {
    this.Kd = Kd;
  }

  public Vector3f getKs() {
    return Ks;
  }

  public void setKs(Vector3f Ks) {
    this.Ks = Ks;
  }

  public Vector3f getKe() {
    return Ke;
  }

  public void setKe(Vector3f Ke) {
    this.Ke = Ke;
  }

  public Integer getMapKa() {
    return mapKa;
  }

  public void setMapKa(Integer mapKa) {
    this.mapKa = mapKa;
  }

  public Integer getMapKd() {
    return mapKd;
  }

  public void setMapKd(Integer mapKd) {
    this.mapKd = mapKd;
  }

  public Integer getMapKs() {
    return mapKs;
  }

  public void setMapKs(Integer mapKs) {
    this.mapKs = mapKs;
  }

  public Integer getMapRefl() {
    return mapRefl;
  }

  public void setMapRefl(Integer mapRefl) {
    this.mapRefl = mapRefl;
  }
}
