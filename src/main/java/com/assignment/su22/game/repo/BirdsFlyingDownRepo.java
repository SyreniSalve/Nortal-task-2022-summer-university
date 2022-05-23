package com.assignment.su22.game.repo;

import java.util.List;

public interface BirdsFlyingDownRepo {
    void findBirdsFlyingDownPosition(char[][] array, List<Integer[]> positionArray);

    void changeBirdsFlyingDownPosition(List<Integer[]> positionArray);

    void gameplayWithNewBirdsFlyingDownPositions(List<Integer[]> positionArray, char[][] array, List<Integer> changesCounter);

    void oneMoveDown(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                     char object);

    void oneMoveToTheZeroPositionColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                        char object, List<Integer> changesCounter);

    void skipOneXInTheEndColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                char object, List<Integer> changesCounter);

    void skipTwoXInTheEndColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                char object, List<Integer> changesCounter);

    void skipOneXInTheMiddleColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                   char object, List<Integer> changesCounter);
}

