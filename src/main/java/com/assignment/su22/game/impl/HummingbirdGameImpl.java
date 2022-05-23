package com.assignment.su22.game.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.assignment.su22.game.HummingbirdGame;
import com.assignment.su22.game.logic.AllBirdsFlyingLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HummingbirdGameImpl extends AllBirdsFlyingLogic implements HummingbirdGame {
    char[][] myArray = new char[3][4];
    List<Integer> changesCounter = new ArrayList<>();

    @Override
    public int hummingbirdGame(Resource resource) throws IOException {
        InputStream in = resource.getInputStream();
        Scanner myReader = new Scanner(new InputStreamReader(in));
        String data;
        String[] dataString = new String[3];
        int y = 0;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            dataString[y] = data;
            y++;
        }

        for (int x = 0; x < myArray.length; x++) {
            myArray[x] = dataString[x].toCharArray();
        }
        log.info(Arrays.deepToString(myArray));

        List<Integer[]> originalRightBirdsPositions = new ArrayList<>();
        List<Integer[]> originalDownBirdsPosition = new ArrayList<>();
        int iterationCounter = 0;
        for (int i = 1; i <= myArray[1].length - 1; i++) {
            gameplayWithNewRightFlyingBirdsPositions(originalRightBirdsPositions, myArray, changesCounter);
            originalRightBirdsPositions.clear();
            gameplayWithNewBirdsFlyingDownPositions(originalDownBirdsPosition, myArray, changesCounter);
            originalDownBirdsPosition.clear();
            if (changesCounter.size() == 7) {
                iterationCounter++;
                log.info("The game has stopped because there are no more moves. Output is: {}", iterationCounter);
                return iterationCounter;
            } else if (i >= myArray[1].length - 1) {
                iterationCounter++;
                log.info("Output is: {}", iterationCounter);
                return iterationCounter;
            } else {
                iterationCounter++;

            }
        }
        iterationCounter++;
        return iterationCounter;
    }
}
