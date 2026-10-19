# Goal Description
Enable users to search for clothes using the Channel3 Fashion & Apparel Product Search API, choose items into specific slots (Top, Bottom, Shoes, Outerwear, Accessory), and save them into the local Room database to plan outfits.

## Proposed Changes

### Component: Data / Remote
#### [NEW] [Channel3FashionApi.kt](file:///C:/Users/Student/OneDrive - ADvTECH Ltd/Documents/apgr/StyleDrop_Project/StyleDrop_Project/StyleDrop-Android/app/src/main/java/com/styledrop/app/data/remote/Channel3FashionApi.kt)
- Define the request/response models and Retrofit interface for the Channel3 Product Search API.
- Include a repository/service wrapper with high-quality fallback products matching Channel3's product database (Micah Pant, Icebreaker Merino Tee, etc.) to ensure instant availability and reliability.

### Component: UI / Outfit Planner
#### [MODIFY] [MainActivity.kt](file:///C:/Users/Student/OneDrive - ADvTECH Ltd/Documents/apgr/StyleDrop_Project/StyleDrop_Project/StyleDrop-Android/app/src/main/java/com/styledrop/app/MainActivity.kt)
- Replace the placeholder `SimpleTab("Outfit Planner", ...)` with a full-featured `OutfitPlannerScreen`.
- Implement slot selection (Top, Bottom, Shoes, Outerwear, Accessory) using the custom Channel3 API search results.
- Render product images with Coil's `AsyncImage`.
- Provide a list view of all saved outfits with their named components.

## Verification Plan
### Manual Verification
- Deploy and verify the Outfit Planner screen.
- Verify search functionality and category switching.
- Verify selecting a product updates the respective outfit slot.
- Verify saving an outfit stores it correctly in the local database and displays it in the saved outfits list.
