package org.rovak;

import org.apache.commons.io.FileUtils;
import org.rovak.events.*;
import org.tron.core.capsule.AccountCapsule;
import org.tron.core.config.args.Args;
import org.tron.core.db.Manager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Logger {

  public static void LogRoundEnded(RoundEnded roundEnded, Manager dbManager) {

    roundEnded.setBlock(dbManager.getHeadBlockNum());
    roundEnded.setTimestamp(dbManager.getHeadBlockTimeStamp());

    Log(roundEnded);
  }
  public static void LogAccountVote(AccountCapsule account, Manager dbManager) {

    AccountVoted accountVoted = new AccountVoted(account);
    accountVoted.setBlock(dbManager.getHeadBlockNum());
    accountVoted.setTimestamp(dbManager.getHeadBlockTimeStamp());

    Log(accountVoted);
  }

  public static void LogClaimRewards(ClaimRewards rewards, Manager dbManager) {
    rewards.setBlock(dbManager.getHeadBlockNum());
    rewards.setTimestamp(dbManager.getHeadBlockTimeStamp());
    Log(rewards);
  }

  public static void LogUnfreeze(UnfreezeBalance rewards, Manager dbManager) {
    rewards.setBlock(dbManager.getHeadBlockNum());
    rewards.setTimestamp(dbManager.getHeadBlockTimeStamp());
    Log(rewards);
  }

  public static <T extends Event> void Log(T event) {

    try {

      String logMessage = Args.getInstance().jsonSerialize.serialize(event);

      FileUtils.writeStringToFile(
              new File("vote-log.txt"),
              logMessage + "\n",
              Charset.forName("utf-8"),
              true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
