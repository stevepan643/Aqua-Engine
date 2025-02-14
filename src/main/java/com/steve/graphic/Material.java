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

  public void setNs(float Ns) {
    this.Ns = Ns;
  }

  public void setNi(float Ni) {
    this.Ni = Ni;
  }

  public void setD(float d) {
    this.d = d;
  }

  public void setTr(float Tr) {
    this.Tr = Tr;
  }

  public void setTf(Vector3f Tf) {
    this.Tf = Tf;
  }

  public void setIllum(int illum) {
    this.illum = illum;
  }

  public void setKa(Vector3f Ka) {
    this.Ka = Ka;
  }

  public void setKd(Vector3f Kd) {
    this.Kd = Kd;
  }

  public void setKs(Vector3f Ks) {
    this.Ks = Ks;
  }

  public void setKe(Vector3f Ke) {
    this.Ke = Ke;
  }

  public void setMapKa(Integer mapKa) {
    this.mapKa = mapKa;
  }

  public void setMapKd(Integer mapKd) {
    this.mapKd = mapKd;
  }

  public void setMapKs(Integer mapKs) {
    this.mapKs = mapKs;
  }

  public void setMapRefl(Integer mapRefl) {
    this.mapRefl = mapRefl;
  }
}
