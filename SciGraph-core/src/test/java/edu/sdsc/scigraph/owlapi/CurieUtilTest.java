/**
 * Copyright (C) 2014 The SciGraph authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.sdsc.scigraph.owlapi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

public class CurieUtilTest {

  CurieUtil util;

  @Before
  public void setup() {
    Map<String, String> map = new HashMap<>();
    map.put("", "http://x.org/");
    map.put("A", "http://x.org/a_");
    map.put("B", "http://x.org/B_");
    util = new CurieUtil(map);
  }

  @Test
  public void curiePrefixes() {
    assertThat(util.getPrefixes(), hasItems("A", "B"));
  }

  @Test
  public void expansion() {
    assertThat(util.getExpansion("A"), is("http://x.org/a_"));
  }

  @Test
  public void absentIri_whenMappingIsNotPresent() {
    assertThat(util.getIri("NONE:foo").isPresent(), is(false));
  }

  @Test
  public void fullIri_whenInputHasNoPrefix() {
    assertThat(util.getIri(":foo").get(), is("http://x.org/foo"));
  }

  @Test
  public void currie_whenShortMappingIsPresent() {
    assertThat(util.getCurie("http://x.org/foo"), is(Optional.of(":foo")));
  }

  @Test
  public void currie_whenLongMappingIsPresent() {
    assertThat(util.getCurie("http://x.org/a_foo"), is(Optional.of("A:foo")));
  }

  @Test
  public void noCurrie_whenMappingIsNotPresent() {
    assertThat(util.getCurie("http://none.org/none"), is(Optional.<String>absent()));
  }

}
