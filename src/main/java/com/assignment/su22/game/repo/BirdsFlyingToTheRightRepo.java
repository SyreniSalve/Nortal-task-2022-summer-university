package com.assignment.su22.game.repo;

import java.util.List;

public interface BirdsFlyingToTheRightRepo {
    int join(int a, int b);

    Integer[] split(int combinedNumber);

    void findRightFlyingBirdsPosition(char[][] array, List<Integer[]> positionArray);

    void changeRightFlyingBirdsPosition(List<Integer[]> positionArray);

    void gameplayWithNewRightFlyingBirdsPositions(List<Integer[]> positionArray, char[][] array, List<Integer> changesCounter);

    void oneMoveToTheRight(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                           char object);

    void oneMoveToTheZeroPositionRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                     char object, List<Integer> changesCounter);

    void skipOneXInTheEndRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                             char object, List<Integer> changesCounter);

    void skipTwoXInTheEndRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                             char object, List<Integer> changesCounter);

    void skipOneXInTheMiddleRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                char object, List<Integer> changesCounter);

    void skipTwoXInTheMiddleRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                char object, List<Integer> changesCounter);
}
