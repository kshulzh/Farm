package me.kshulzh.farm.api

import me.kshulzh.farm.dto.*
import org.springframework.web.bind.annotation.*

@RequestMapping("/feed-service")
interface FeedServiceHTTP : FeedService {
    @PostMapping("/receipts")
    override fun addReceipt(@RequestBody feedRecipe: FeedRecipeDto)
    @PutMapping("/receipts")
    override fun editReceipt(@RequestBody feedRecipe: FeedRecipeDto)
    @DeleteMapping("/receipts/{id}")
    override fun deleteReceipt(@PathVariable("id") feedRecipeId: String)
    @GetMapping("/receipts/{id}")
    override fun getReceipt(@PathVariable("id") feedRecipeId: String) : FeedRecipeDto
    @GetMapping("/receipts")
    override fun getReceipts(criteria: Map<String,String>?) : List<FeedRecipeDto>

    @PostMapping("/ingredients")
    override fun addIngredient(@RequestBody ingredient: FeedIngredientDto)
    @PutMapping("/ingredients")
    override fun editIngredient(@RequestBody ingredient: FeedIngredientDto)
    @DeleteMapping("/ingredients/{id}")
    override fun deleteIngredient(@PathVariable("id") ingredientId: String)
    @GetMapping("/ingredients/{id}")
    override fun getIngredient(@PathVariable("id") ingredientId: String) : FeedIngredientDto
    @GetMapping("/ingredients")
    override fun getIngredients(criteria: Map<String,String>?) : List<FeedIngredientDto>

    @PostMapping("/fill-storage")
    override fun fillStorage(@RequestBody ingredient: FillStorageDto)
    @GetMapping("/leftovers/{id}")
    override fun getLeftover(@PathVariable("id") ingredientId: String) : FeedIngredientDto
    @GetMapping("/leftovers")
    override fun getLeftovers(criteria: Map<String,String>?) : List<FeedIngredientDto>

    @PostMapping("/produce")
    override fun produce(@RequestBody produceFeedDto: ProduceFeedDto)
    @PostMapping("/feed")
    override fun feed(@RequestBody feedDto: FeedDto)
}