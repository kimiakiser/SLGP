package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.TablarDAO;

public class TablarDAOImpl extends GenericPersisterDAOImpl<Tablar> implements TablarDAO {

    public TablarDAOImpl() {
        super(Tablar.class);
    }
}
