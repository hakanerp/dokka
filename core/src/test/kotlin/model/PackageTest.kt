package org.jetbrains.dokka.tests

import org.jetbrains.dokka.Content
import org.jetbrains.dokka.NodeKind
import org.jetbrains.kotlin.config.KotlinSourceRoot
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

public class PackageTest {
    @Test fun rootPackage() {
        verifyModel("testdata/packages/rootPackage.kt") { model ->
            with(model.members.single()) {
                assertEquals(NodeKind.Package, kind)
                assertEquals("", name)
                assertEquals(Content.Empty, content)
                assertTrue(details.none())
                assertTrue(members.none())
                assertTrue(links.none())
            }
        }
    }

    @Test fun simpleNamePackage() {
        verifyModel("testdata/packages/simpleNamePackage.kt") { model ->
            with(model.members.single()) {
                assertEquals(NodeKind.Package, kind)
                assertEquals("simple", name)
                assertEquals(Content.Empty, content)
                assertTrue(details.none())
                assertTrue(members.none())
                assertTrue(links.none())
            }
        }
    }

    @Test fun dottedNamePackage() {
        verifyModel("testdata/packages/dottedNamePackage.kt") { model ->
            with(model.members.single()) {
                assertEquals(NodeKind.Package, kind)
                assertEquals("dot.name", name)
                assertEquals(Content.Empty, content)
                assertTrue(details.none())
                assertTrue(members.none())
                assertTrue(links.none())
            }
        }
    }

    @Test fun multipleFiles() {
        verifyModel(KotlinSourceRoot("testdata/packages/dottedNamePackage.kt"),
                    KotlinSourceRoot("testdata/packages/simpleNamePackage.kt")) { model ->
            assertEquals(2, model.members.count())
            with(model.members.single { it.name == "simple" }) {
                assertEquals(NodeKind.Package, kind)
                assertEquals("simple", name)
                assertEquals(Content.Empty, content)
                assertTrue(details.none())
                assertTrue(members.none())
                assertTrue(links.none())
            }
            with(model.members.single { it.name == "dot.name" }) {
                assertEquals(NodeKind.Package, kind)
                assertEquals(Content.Empty, content)
                assertTrue(details.none())
                assertTrue(members.none())
                assertTrue(links.none())
            }
        }
    }

    @Test fun multipleFilesSamePackage() {
        verifyModel(KotlinSourceRoot("testdata/packages/simpleNamePackage.kt"),
                    KotlinSourceRoot("testdata/packages/simpleNamePackage2.kt")) { model ->
            assertEquals(1, model.members.count())
            with(model.members.elementAt(0)) {
                assertEquals(NodeKind.Package, kind)
                assertEquals("simple", name)
                assertEquals(Content.Empty, content)
                assertTrue(details.none())
                assertTrue(members.none())
                assertTrue(links.none())
            }
        }
    }
}