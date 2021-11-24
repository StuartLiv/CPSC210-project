# Calorie and Macronutrient Tracker

## Project Description

This project aims to offload the calculations and tracking of food intake that is common to dieters, gym goers and 
anyone concerned about their nutrition. While nutritional information is usually on food packaging, inconvenient serving
sizes and numbers cumbersome to do mental calculations with make doing this on your own difficult. The goal of this 
application is to have users enter minimal data about the food they ate, and then using past entries and a standardized 
database of nutritional information, track the:

- Calories
- Protein
- Carbohydrates
- Fat
- Overall mass of food
- Time of meals

Users can store recipes with measured amounts of food as *frequent meals* to make entering them again easier, add 
ingredients they use to the database, see daily summary statistics about their food, compare information about different
ingredients, observe diet changes over time and more. 

As someone who currently tries to track this information, I have yet to find a method better than doing it in my head 
and only daily tracking, and not over a longer period of time, as to maintain and calculate this data on my own is far 
too time-consuming to be worth it. I believe that a major barrier to self-improvement is lack of digestible information
and a large time commitment just to get started, and with this project, I aim to make starting, and keeping to your 
nutritional goals easier.

## User Stories

### Phase One User Stories
- As a user, I want to add a meal to my meal history
- As a user, I want to add multiple unknown ingredients to the ingredient database
- As a user, I want to add a recipe to my recipe book
- As a user, I want to see summary statistics about my daily nutrition

### Phase Two User Stories
- As a user, I want to select a profile with user history to load
- As a user, I want to save my application state
- As a user, I want to be reminded to save the application state when I exit the program

### Phase Three User Stories
- As a user, I want to graphically add, edit, delete and see ingredients


##Phase 4 Task 2:
Sat Nov 20 14:25:47 PST 2021
Profile data read from ./data/stuart/
Sat Nov 20 14:26:35 PST 2021
Added Ingredient: Peanut Butter
Sat Nov 20 14:26:35 PST 2021
Added Ingredient: Protein Bread
Sat Nov 20 14:27:46 PST 2021
Added Recipe: Strawberry Peanut Toast
Sat Nov 20 14:28:08 PST 2021
Added Meal: Strawberry Peanut Toast at 2021-11-20T10:00
Sat Nov 20 14:28:19 PST 2021
Deleted Ingredient: Lettuce
Sat Nov 20 14:29:02 PST 2021
Added Ingredient: Oat Milk

##Phase 4 Task 3:
Potential refactoring changes:
- Superclass for the different JPanels in MainPanel, as they all have the same associations
- Implement observer pattern on new superclass, move MainPanel.updatePanels behavior te be observer dependant
- Refactor doIngredient, doRecipe, doMeal into one function, using functions as parameters
- Migrate getDailyTotal functionality to profile, using a dailyTotals field of type ArrayList<Portion> 
- Change current add and delete field in profile method of editing to new editField method, for clearer debugging
- Similar inside classes in gui panel classes could be given type hierarchy, and refactored to remove similar code
- Json methods could be storage optimized to store to one file, as storing to multiple files is somewhat redundant
- Many ConsoleUI methods could be refactored, to decrease duplicate/near duplicate code
