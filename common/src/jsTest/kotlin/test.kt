import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import me.kshulzh.farm.client.AnimalServiceHttpClient
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.http.HttpClientImpl
import kotlin.test.Test

/*
 *   Copyright (c) 2023. Kyrylo Shulzhenko
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

class aTest {

    @Test
    fun a() = runTest {
        val b = HttpClientImpl("http://127.0.0.1:8080")
        launch {
            val c = b.getById<AnimalDto>("/animals-service/animals", "46")
            println(c.gender)
        }.start()
    }

    @Test
    fun n() = runTest {
        val b = AnimalServiceHttpClient(HttpClientImpl("http://127.0.0.1:8080"))
        launch {
            val c = b.addAnimal(AnimalDto().apply {
                id = "124556"
            })
            println(c.id)
        }.start()
    }
}