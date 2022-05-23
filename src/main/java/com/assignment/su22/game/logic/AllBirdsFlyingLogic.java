package com.assignment.su22.game.logic;

import com.assignment.su22.game.repo.BirdsFlyingDownRepo;
import com.assignment.su22.game.repo.BirdsFlyingToTheRightRepo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AllBirdsFlyingLogic implements BirdsFlyingToTheRightRepo, BirdsFlyingDownRepo {
    @Override
    public int join(int a, int b) {
        int times = 1;
        times *= 10;
        return a * times + b;
    }

    @Override
    public Integer[] split(int combinedNumber) {
        Integer[] position = new Integer[2];
        position[0] = combinedNumber / 10;
        position[1] = combinedNumber % 10;
        return position;
    }

    @Override
    public void findRightFlyingBirdsPosition(char[][] array, List<Integer[]> positionArray) {
        for (int y = 0; y < array.length; y++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[y][j] == ('>')) {
                    Integer[] position = {y, j};
                    positionArray.add(position);
                }
            }
        }
    }

    @Override
    public void changeRightFlyingBirdsPosition(List<Integer[]> positionArray) {
        Set<Integer[]> oneLineSet = new HashSet<>();
        List<Integer[]> arrayCopy = new ArrayList<>();
        int i = 0;
        int counter = 0;
        while (i < positionArray.size()) {
            oneLineSet.clear();
            Integer[] numbers = positionArray.get(i);
            int j = 1 + counter;
            while (j < positionArray.size()) {
                Integer[] numbersArr = positionArray.get(j);
                if (numbers[0].equals(numbersArr[0])) {
                    oneLineSet.add(numbers);
                    oneLineSet.add(numbersArr);
                }
                j++;
            }
            if (oneLineSet.size() == 0 && positionArray.size() == 1 || positionArray.size() == 2) {
                break;
            } else if (oneLineSet.size() == 0) {
                i++;
                counter++;
            } else {
                positionArray.removeAll(oneLineSet);

                int max = 0;
                int min = 1;
                for (Integer[] ols : oneLineSet) {
                    if (ols[1] <= min) {
                        min = ols[1];
                    } else if (ols[1] > max) {
                        max = ols[1];
                    }
                }
                positionSorting(min, max, arrayCopy, oneLineSet);
            }
        }
        positionArray.addAll(arrayCopy);
    }

    public void positionSorting (int min, int max, List<Integer[]> positions, Set<Integer[]> setPositions) {
        List<Integer> joinedOllArr = new ArrayList<>();
        List<Integer[]> oneLineList = new ArrayList<>(setPositions);
        if (min == 0 && max == 3) {
            for (Integer[] oll : oneLineList) {
                joinedOllArr.add(join(oll[0], oll[1]));
            }
            Collections.sort(joinedOllArr);
            oneLineList.clear();
            for (int number : joinedOllArr) {
                oneLineList.add(split(number));
            }

        } else {
            for (Integer[] oll : oneLineList) {
                joinedOllArr.add(join(oll[0], oll[1]));
            }
            joinedOllArr.sort(Collections.reverseOrder());
            oneLineList.clear();
            for (int number : joinedOllArr) {
                oneLineList.add(split(number));
            }
        }
        positions.addAll(oneLineList);
    }

    @Override
    public void gameplayWithNewRightFlyingBirdsPositions(List<Integer[]> positionArray,
                                                         char[][] array, List<Integer> changesCounter) {
        findRightFlyingBirdsPosition(array, positionArray);
        changeRightFlyingBirdsPosition(positionArray);
        for (Integer[] positionNumbers : positionArray) {
            char t = array[positionNumbers[0]][positionNumbers[1]];
            if (positionNumbers[1] != array[1].length - 1) {
                if (array[positionNumbers[0]][positionNumbers[1] + 1] == ('.')) {
                    oneMoveToTheRight(array, positionNumbers[0], positionNumbers[1], t);
                    continue;
                }
                if (array[positionNumbers[0]][positionNumbers[1] + 1] == ('x')
                        && positionNumbers[1] + 1 == array[1].length - 1) {
                    skipOneXInTheEndRow(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    continue;
                }
                if (array[positionNumbers[0]][positionNumbers[1] + 1] == ('x')) {
                    if (array[positionNumbers[0]][positionNumbers[1] + 1] == ('x') &&
                            array[positionNumbers[0]][positionNumbers[1] + 2] == ('.') &&
                            positionNumbers[1] + 1 != array[1].length - 1) {
                        skipOneXInTheMiddleRow(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    } else if (array[positionNumbers[0]][positionNumbers[1] + 1] == ('x') &&
                            array[positionNumbers[0]][positionNumbers[1] + 2] == ('>') ||
                            array[positionNumbers[0]][positionNumbers[1] + 2] == ('v') &&
                                    positionNumbers[1] + 1 != array[1].length - 1) {
                        skipOneXInTheMiddleRow(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    } else if (positionNumbers[1] == 0 &&
                            array[positionNumbers[0]][positionNumbers[1] + 2] == ('x') &&
                            positionNumbers[1] + 2 != array[1].length - 1) {
                        skipTwoXInTheMiddleRow(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    } else {
                        skipTwoXInTheEndRow(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    }
                }
            } else if (positionNumbers[1] == array[1].length - 1) {
                oneMoveToTheZeroPositionRow(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
            }
        }

    }


    @Override
    public void oneMoveToTheRight(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber, char object) {
        int newPosition = secondPositionNumber + 1;
        array[firstPositionNumber][secondPositionNumber] = array[firstPositionNumber][newPosition];
        array[firstPositionNumber][newPosition] = object;
    }

    @Override
    public void oneMoveToTheZeroPositionRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                            char object, List<Integer> changesCounter) {
        int newPosition = 0;
        if (array[firstPositionNumber][newPosition] == ('x') &&
                array[firstPositionNumber][newPosition + 1] == ('x') &&
                array[firstPositionNumber][newPosition + 2] == ('x')) {
            newPosition = secondPositionNumber;
        }
        if (array[firstPositionNumber][newPosition] == ('>')
                || array[firstPositionNumber][newPosition] == ('v')) {
            newPosition = array[0].length - 1;
        }
        array[firstPositionNumber][secondPositionNumber] = array[firstPositionNumber][newPosition];
        array[firstPositionNumber][newPosition] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }
    }

    @Override
    public void skipOneXInTheEndRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                    char object, List<Integer> changesCounter) {
        int newPosition = 0;
        if (array[firstPositionNumber][newPosition] == ('>')
                || array[firstPositionNumber][newPosition] == ('v')) {
            newPosition = array[0].length - 2;
        }
        array[firstPositionNumber][secondPositionNumber] = array[firstPositionNumber][newPosition];
        array[firstPositionNumber][newPosition] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }
    }

    @Override
    public void skipTwoXInTheEndRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                    char object, List<Integer> changesCounter) {
        int newPosition = 0;
        if (array[firstPositionNumber][newPosition] == ('>')
                || array[firstPositionNumber][newPosition] == ('v')) {
            newPosition = array[0].length - 3;
        }
        array[firstPositionNumber][secondPositionNumber] = array[firstPositionNumber][newPosition];
        array[firstPositionNumber][newPosition] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }
    }

    @Override
    public void skipOneXInTheMiddleRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                       char object, List<Integer> changesCounter) {
        int newPosition = secondPositionNumber + 2;
        if (array[firstPositionNumber][newPosition] == ('>')
                || array[firstPositionNumber][newPosition] == ('v')) {
            newPosition = secondPositionNumber;
        }
        array[firstPositionNumber][secondPositionNumber] = array[firstPositionNumber][newPosition];
        array[firstPositionNumber][newPosition] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }
    }

    @Override
    public void skipTwoXInTheMiddleRow(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                       char object, List<Integer> changesCounter) {
        int newPosition = secondPositionNumber + 3;
        if (array[firstPositionNumber][newPosition] == ('>') ||
                array[firstPositionNumber][newPosition] == ('v') ||
                array[firstPositionNumber][newPosition] == ('x') &&
                        array[firstPositionNumber][newPosition - 1] == ('x') &&
                        array[firstPositionNumber][newPosition - 2] == ('x')) {
            newPosition = secondPositionNumber;
        }
        array[firstPositionNumber][secondPositionNumber] = array[firstPositionNumber][newPosition];
        array[firstPositionNumber][newPosition] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }
    }

    @Override
    public void findBirdsFlyingDownPosition(char[][] array, List<Integer[]> positionArray) {
        for (int y = 0; y < array.length; y++) {
            for (int j = 0; j < array[0].length; j++)
                if (array[y][j] == ('v')) {
                    Integer[] position = {y, j};
                    positionArray.add(position);
                }
        }
    }

    @Override
    public void changeBirdsFlyingDownPosition(List<Integer[]> positionArray) {
        Set<Integer[]> oneLineSet = new HashSet<>();
        List<Integer[]> arrayCopy = new ArrayList<>();
        int i = 0;
        int counter = 0;
        while (i < positionArray.size()) {
            oneLineSet.clear();
            Integer[] numbers = positionArray.get(i);
            int j = 1 + counter;
            while (j < positionArray.size()) {
                Integer[] numbersArr = positionArray.get(j);
                if (numbers[1].equals(numbersArr[1])) {
                    oneLineSet.add(numbers);
                    oneLineSet.add(numbersArr);
                }
                j++;
            }
            if (oneLineSet.size() == 0 && positionArray.size() == 1) {
                break;
            } else if (oneLineSet.size() == 0) {
                i++;
                counter++;
            } else {
                positionArray.removeAll(oneLineSet);

                int max = 0;
                int min = 1;
                for (Integer[] ols : oneLineSet) {
                    if (ols[0] <= min) {
                        min = ols[0];
                    } else if (ols[0] > max) {
                        max = ols[0];
                    }
                    log.info(Arrays.deepToString(ols));

                }
                positionSorting(min, max, arrayCopy, oneLineSet);
            }

        }
        positionArray.addAll(arrayCopy);
    }

    @Override
    public void gameplayWithNewBirdsFlyingDownPositions(List<Integer[]> positionArray, char[][] array,
                                                        List<Integer> changesCounter) {
        findBirdsFlyingDownPosition(array, positionArray);
        changeBirdsFlyingDownPosition(positionArray);
        for (Integer[] positionNumbers : positionArray) {
            char t = array[positionNumbers[0]][positionNumbers[1]];
            if (positionNumbers[0] + 1 != 3) {
                if (array[positionNumbers[0] + 1][positionNumbers[1]] == ('.')) {
                    oneMoveDown(array, positionNumbers[0], positionNumbers[1], t);
                    continue;
                }
                if (array[positionNumbers[0] + 1][positionNumbers[1]] == ('x')
                        && positionNumbers[0] + 1 == 3) {
                    skipOneXInTheEndColumn(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    continue;
                }
                if (array[positionNumbers[0] + 1][positionNumbers[1]] == ('x') ||
                        array[positionNumbers[0] + 1][positionNumbers[1]] == ('>') ||
                        array[positionNumbers[0] + 1][positionNumbers[1]] == ('v')) {
                    if (array[positionNumbers[0] + 1][positionNumbers[1]] == ('X') &&
                            positionNumbers[0] + 1 != 3) {
                        skipOneXInTheMiddleColumn(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    } else if (array[positionNumbers[0] + 1][positionNumbers[1]] == ('>') ||
                            array[positionNumbers[0] + 1][positionNumbers[1]] == ('V')) {
                        changesCounter.add(1);
                    } else {
                        skipTwoXInTheEndColumn(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
                    }
                }
            } else {
                oneMoveToTheZeroPositionColumn(array, positionNumbers[0], positionNumbers[1], t, changesCounter);
            }
        }
    }

    @Override
    public void oneMoveDown(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber, char object) {
        int newPosition = firstPositionNumber + 1;
        array[firstPositionNumber][secondPositionNumber] = array[newPosition][secondPositionNumber];
        array[newPosition][secondPositionNumber] = object;
    }

    @Override
    public void oneMoveToTheZeroPositionColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                               char object, List<Integer> changesCounter) {
        int newPosition = 0;
        if (array[newPosition][secondPositionNumber] == ('x') &&
                array[newPosition + 1][secondPositionNumber] == ('x') ||
                array[newPosition][secondPositionNumber] == ('>')
                || array[newPosition][secondPositionNumber] == ('v')) {
            newPosition = firstPositionNumber;
        }
        array[firstPositionNumber][secondPositionNumber] = array[newPosition][secondPositionNumber];
        array[newPosition][secondPositionNumber] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }
    }

    @Override
    public void skipOneXInTheEndColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                       char object, List<Integer> changesCounter) {
        int newPosition = 0;
        if (array[newPosition][secondPositionNumber] == ('>')
                || array[newPosition][secondPositionNumber] == ('v')) {
            newPosition = array.length - 1;
        }
        array[firstPositionNumber][secondPositionNumber] = array[newPosition][secondPositionNumber];
        array[newPosition][secondPositionNumber] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }

    }

    @Override
    public void skipTwoXInTheEndColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                       char object, List<Integer> changesCounter) {
        int newPosition = 0;
        if (array[newPosition][secondPositionNumber] == ('>')
                || array[newPosition][secondPositionNumber] == ('v')) {
            newPosition = array.length - 2;
        }
        array[firstPositionNumber][secondPositionNumber] = array[newPosition][secondPositionNumber];
        array[newPosition][secondPositionNumber] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }

    }

    @Override
    public void skipOneXInTheMiddleColumn(char[][] array, Integer firstPositionNumber, Integer secondPositionNumber,
                                          char object, List<Integer> changesCounter) {
        int newPosition = firstPositionNumber + 2;
        if (array[newPosition][secondPositionNumber] == ('>')
                || array[newPosition][secondPositionNumber] == ('v') ||
                array[newPosition][secondPositionNumber] == ('x')) {
            newPosition = firstPositionNumber;
        }
        array[firstPositionNumber][secondPositionNumber] = array[newPosition][secondPositionNumber];
        array[newPosition][secondPositionNumber] = object;
        if (secondPositionNumber == newPosition) {
            changesCounter.add(1);
        }
    }
}
