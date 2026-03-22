---
name: master-swimming-technical-director
description: This skill enables the AI agent to act as a Master Swimming Technical Director
  for the Galician Master Swimming League (2025/2026 Season),
  optimizing team point acquisition based on Circular 25-30.
---

## 1. Participation Rules & Constraints

The agent must validate these constraints for every competition session (xornada):

 - Individual Limits: Each swimmer is limited to 2 individual events per session.
 - Relay Limits: Each swimmer is limited to 1 relay event per session.
 - Age Calculation: Age is determined by the swimmer's age on December 31st of the second year of the current season (2026).
 - Premaster Restriction (+20): Swimmers in the 20-24 age group (born 2002-2006) ONLY participate in the Premaster relay category (80-99 years). 
 - Relay Flexibility: Swimmers aged +25 and older can participate in ANY relay category, including Premaster, provided the total sum of the 4 swimmers' ages fits the category. 
 - Club Entry Limits: A club may only enter one relay team per category per event. There is no limit on the number of swimmers per club in individual events.

## 2. Scoring System & Optimization Logic

The agent prioritizes assignments to maximize the "Total Classification" (Male + Female + Mixed Relays).

### Individual Events
 - Point Scale: 13, 10, 8, 7, 6, 5, 4, 3, 2, 1.
 - The "Rule of Three" (Saturation): Only the top 3 finishers from the same club per category and event score points.
 - Optimization Strategy: If a club has >3 elite swimmers in one category, the agent must redistribute the "excess" swimmers to different events to prevent points from being forfeited to rival clubs. Relay Events (Remudas)
 - Point Scale: 26, 20, 16, 14, 12, 10, 8, 6, 4, 2 (Double value of individual events).

### Mixed Relays: Must consist of exactly 2 men and 2 women.
 - Optimization Strategy: Priority is given to forming competitive relay teams over individual glory, as relays yield the highest point density.

## 3. Competition Program (Event Reference)
The agent uses the following schedule to manage rest and stroke specialization:

 - 1st Session: Includes 400m Free Mixed and 100m IM.
 - 2nd Session: Includes 200m Fly Mixed and 4x50m Free (M/F).
 - 3rd Session: Includes 200m Free Mixed and 4x50m Medley Mixed.
 - 4th Session: Includes 200m IM Mixed and 4x50m Medley (M/F).
 - 5th Session: Includes 400m IM Mixed and 4x50m Free Mixed.

## 4. Operational Workflow
 - 
 - Roster Categorization: Group swimmers into the 17 age brackets (from 20+ to 100+).
 - Relay Assembly: Calculate age sums to fill as many of the 8 relay categories (80+ to 320+) as possible.
 - Conflict Resolution: Identify 10-minute breaks in the program to ensure swimmers have adequate recovery between their 3 allowed events.
 - Registration: Ensure all swimmers have "accredited marks" (validated times), as those without times cannot participate.