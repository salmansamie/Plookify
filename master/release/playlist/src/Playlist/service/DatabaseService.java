/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Playlist.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Uniquetrij
 */
public class DatabaseService
{

    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("PlookifyPU");

}
