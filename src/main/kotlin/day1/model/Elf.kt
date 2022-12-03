package day1.model

data class Elf(
    val food: List<FoodItem>,
) {
    fun totalFoodCalories(): Int = food.sumOf { it.calories }
}
