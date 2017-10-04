# InformationRetrieval
Simple document lookup application. Uses HTML source of the Wikipedia pages of US presidents as a document library.

Scores documents with a combination of the BM25 algorithm and skip bigrams.

### Use and examples
```
> speak softly
1. TeddyRoosevelt.txt (1.000)

> assassinated
1. Garfield.txt (1.000)
2. Taylor.txt (0.837)
3. McKinley.txt (0.641)
4. Harrison.txt (0.364)
5. Kennedy.txt (0.343)
6. Taft.txt (0.270)
7. Lincoln.txt (0.199)
8. Eisenhower.txt (0.038)
9. LBJ.txt (0.035)

> world war ii
1. FDR.txt (1.000)
2. Hoover.txt (0.845)
3. Eisenhower.txt (0.611)
4. Truman.txt (0.393)
5. Bush.txt (0.231)
6. Wilson.txt (0.186)
7. Kennedy.txt (0.181)
8. Taylor.txt (0.178)
9. Coolidge.txt (0.178)
10. Ford.txt (0.136)

> watergate
1. Nixon.txt (1.000)
2. Ford.txt (0.292)
3. Carter.txt (0.077)
4. Bush.txt (0.076)
5. Harding.txt (0.041)
6. Truman.txt (0.039)
7. Clinton.txt (0.035)
```
