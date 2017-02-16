/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.data.index.elasticsearch.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import org.junit.Test;

import org.kitodo.data.database.beans.User;
import org.kitodo.data.database.beans.UserGroup;

import static org.junit.Assert.*;

/**
 * Test class for UserGroupType.
 */
public class UserGroupTypeTest {

    private static List<UserGroup> prepareData() {

        List<User> users = new ArrayList<>();
        List<UserGroup> userGroups = new ArrayList<>();

        User firstUser = new User();
        firstUser.setId(1);
        users.add(firstUser);

        User secondUser = new User();
        secondUser.setId(2);
        users.add(secondUser);

        UserGroup firstUserGroup = new UserGroup();
        firstUserGroup.setId(1);
        firstUserGroup.setTitle("Administrator");
        firstUserGroup.setPermission(1);
        firstUserGroup.setUsers(users);
        userGroups.add(firstUserGroup);

        UserGroup secondUserGroup = new UserGroup();
        secondUserGroup.setId(2);
        secondUserGroup.setTitle("Random");
        userGroups.add(secondUserGroup);

        return userGroups;
    }

    @Test
    public void shouldCreateDocument() throws Exception {
        UserGroupType userGroupType = new UserGroupType();

        UserGroup userGroup = prepareData().get(0);
        HttpEntity document = userGroupType.createDocument(userGroup);
        String actual = EntityUtils.toString(document);
        String excepted = "{\"title\":\"Administrator\",\"permission\":\"1\",\"users\":[{\"id\":\"1\"},{\"id\":\"2\"}]}";
        assertEquals("UserGroup JSON string doesn't match to given plain text!", excepted, actual);

        userGroup = prepareData().get(1);
        document = userGroupType.createDocument(userGroup);
        actual = EntityUtils.toString(document);
        excepted = "{\"title\":\"Random\",\"permission\":\"4\",\"users\":[]}";
        assertEquals("Batch JSON string doesn't match to given plain text!", excepted, actual);
    }

    @Test
    public void shouldCreateDocuments() throws Exception {
        UserGroupType UserGroupType = new UserGroupType();

        List<UserGroup> batches = prepareData();
        HashMap<Integer, HttpEntity> documents = UserGroupType.createDocuments(batches);
        assertEquals("HashMap of documents doesn't contain given amount of elements!", 2, documents.size());
    }
}
