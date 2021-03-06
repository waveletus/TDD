/*
 * %filespec:% %date_created: %
 * %created_by: % %state: %
 *
 * %full_filespec: %
 * %version:%
 */
package com.apress.tddbook;

import java.util.Vector;

public class PoolDatabaseStub implements PoolDatabase {
    public Vector getPoolWithStatus(String status) {
        Vector poolList = new Vector();
        FootballPool pool = new FootballPool("9/17/2003");
        pool.addGame("Kansas City", "Green Bay");
        pool.addGame("Houston", "Tennessee");
        pool.addGame("Carolina", "Indianapolis");
        pool.addGame("NY Giants", "New England");
        pool.addGame("Chicago", "New Orleans");
        pool.addGame("Oakland", "Cleveland");
        pool.addGame("Philadelphia", "Dallas");
        pool.addGame("Tampa Bay", "Washington");
        pool.addGame("Miami", "Jacksonville");
        pool.addGame("Pittsburgh", "Denver");
        pool.addGame("Buffalo", "NY Jets");
        pool.addGame("Baltimore", "Arizona");
        pool.addGame("San Francisco", "Seattle");
        pool.addGame("Atlanta", "St. Louis");
        poolList.addElement(pool);
        return (poolList);
    }

    public Vector getAllPoolDates()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public FootballPool getPoolWithDate(String status)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void savePlayersPicks(PlayersPicks picks)
    {
    }

    public void setPoolStatus(String poolDate, String status)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public PlayersPicks getPlayersPicks(String name, String poolDate)
    {
        return null;
    }
}
