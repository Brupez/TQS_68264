## d) Document the AI “collaboration”: which prompt strategy did you use? Which points/changes did you find relevant/interesting from the AI suggestions? Which refactoring would you effectively recommend to your college to include in production? Try to refer to the suggested refactoring practices using their “well-known” names in code refactoring catalogs1 (e.g.: “Replace Magic Numbers”). 
- The prompt that i use where "Replace Magic Numbers", "Replace command with function" and "Replace Constructor with Factory" and this where the output of copilot:

- Suggested by CoPilot:

TripServiceImpl Review:
- Missing required imports
- Unimplemented getTrips method
- No input validation
- Basic error handling using RuntimeException

ReservationServiceImpl Review:
-Complex methods with multiple responsibilities
-Direct exception propagation
-No transaction management annotations

Key Recommendations:

- Add @Transactional annotations
- Use @Validated and Bean Validation
- Create dedicated exception classes
- Break down complex methods
- Add proper response status codes
- Use constructor injection instead of field injection
- Add comprehensive logging
- Implement proper error handling
- Add unit tests for all service methods
- Use DTOs for request/response handling


## e) Code reviews are expected to improve the code and the programmer. How did you feel about this last point, especially compared with the experience with SonarQube?
- I prefer the SonarQube method to do code review because it's more automated and analyse the code in a more efficient way. The AI suggestions are good but not as accurate as SonarQube which not excludes AI code reviews.