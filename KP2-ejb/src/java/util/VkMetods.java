package util;

import UtilInterfaces.IVkMetods;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import models.Account;
import models.Attachments;
import models.Group;
import models.NewsFeed;
import models.NewsFeedVK;
import models.Vk;

@Stateless
public class VkMetods implements IVkMetods {

    public NewsFeed getNewsVk(Vk vk) {
        //получаю json ответ с лентой новостей
        String ret = HttpURL.httpsGet("https://api.vk.com/method/newsfeed.get?filters=post&count=30&"
                + "access_token=" + vk.getAccessToken());
        String SaveRet = ret;
        //объявляю возвращаемый класс с массивом новостей и переменной для получения следуюшей партии новостей
        NewsFeed nf = new NewsFeed();
        //сохраняю значение переменной new_from для получения следующей части списка новостей 
        int index0 = ret.indexOf("\"new_from\":\"");
        String new_from = ret.substring(index0 + 12, ret.length() - 3);
        //сохраняю значение переменной new_from для получения следующей части списка новостей в возвращаемый объект
        nf.setNewsFeedVKnew_from(new_from);
        //убираю начало и конец ответа из строки
        ret = ret.replace("{\"response\":{\"items\":[", "");
        int index1 = ret.indexOf("}}],\"profiles\":");
        ret = ret.substring(0, index1 + 2);
        //бью строку ответа на строковый массив с разделителем по рег выражению "\\d\\}\\}\\,\\{"
        String[] ns = ret.split("\\d\\}\\}\\,\\{");
        //компенсирую удалённые разделители при разбивки
        ns[0] = ns[0] + "0}}";
        ns[ns.length - 1] = "{" + ns[ns.length - 1];
        for (int i = 1; i < ns.length - 1; i++) {
            ns[i] = "{" + ns[i] + "0}}";
        }
        //объявляю ориентированный массив новостей
        NewsFeedVK[] nfslist = new NewsFeedVK[ns.length];
        //счётчик по элемментам массива
        for (int i = 0; i < ns.length; i++) {
            //перевожу строковую переменную с новостью из json'а в ориентированный формат, конкретно общую информацию без вложений
            NewsFeedVK nfs = (NewsFeedVK) XStreamFactory.get(NewsFeedVK.class, ns[i]);
            //беру переменную со строкой    
            String temp = ns[i];
            //создаю строковую переменную исключтельно с вложениями в новость(Attachments)
            int index2 = temp.indexOf("\"attachments\":[{\"");
            int index3 = temp.indexOf("],\"post_source\":");

            if (index2 == -1 || index3 == -1) {
                nfslist[i] = nfs;
                continue;
            }
            String atts = temp.substring(index2 + 15, index3);
            //бью строку с вложениями на массив строк по отдельным вложениям
            String[] attsmas = atts.split("\\}\\,\\{");
            //компенсирую разделители
            if (attsmas.length > 1) {
                attsmas[0] = attsmas[0] + "}";
                attsmas[attsmas.length - 1] = "{" + attsmas[attsmas.length - 1];
                for (int j = 1; j < attsmas.length - 1; j++) {
                    attsmas[j] = "{" + attsmas[j] + "}";
                }
            }
            //объявляю ориентированный массив вложений в новость
            Attachments[] ats = new Attachments[attsmas.length];
            //счётчик по элемментам массива(перебираю вложения)
            for (int j = 0; j < attsmas.length; j++) {
                //перевожу строковую переменную с вложением из json'а в ориентированный формат, конкретно тип вложения(type)
                Attachments at = (Attachments) XStreamFactory.get(Attachments.class, attsmas[j]);
                //сохраняю тип вложения(type) во временную переменную
                String stemp1 = at.getType();
                //удаляю тип вложения(type) из строковой переменной с вложением
                int index4 = attsmas[j].indexOf("\":{");
                String stemp = attsmas[j].substring(index4 + 2, attsmas[j].length() - 1);
                //перевожу в ориентированный формат остальную информацию о вложении
                at = (Attachments) XStreamFactory.get(Attachments.class, stemp);
                //добавляю тип вложения(type) к ориентированной переменной вложения
                at.setType(stemp1);
                //получаю значение плеера для встраивания видео
                if ("video".equals(stemp1)) {
                    String getVurl = "https://api.vk.com/method/video.get?owner_id=" + at.getOwner_id() + "&videos=" + at.getOwner_id() + "_" + at.getVid() + "_" + at.getAccess_key() + "&extended=0&access_token=" + vk.getAccessToken();
                    String ret1 = HttpURL.httpsGet(getVurl);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(VkMetods.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int index5 = ret1.indexOf("\"player\":\"") + 10;
                    int index6 = ret1.indexOf("\"}]}");
                    at.setPlayer(ret1.substring(index5, index6));
                }
                //добавляю в ориентированный массив вложений ориентированную переменную вложения
                ats[j] = at;
            }
            //добавляю в ячейку ориентированного массива новостей ориентированный массив вложений
            nfs.setAtts(ats);
            //добавляю в ориентированный массив новостей ориентированную переменную новости с вложениями
            nfslist[i] = nfs;
        }

        int index2 = SaveRet.indexOf("\"profiles\":[");
        int index3 = SaveRet.indexOf("],\"groups\":[");
        if (index2 != -1 || index3 != -1) {
            String accounts = SaveRet.substring(index2 + 12, index3);
            String[] accs = accounts.split("\\}\\,\\{");
            if (accs.length > 1) {
                accs[0] = accs[0] + "}";
                accs[accs.length - 1] = "{" + accs[accs.length - 1];
                for (int j = 1; j < accs.length - 1; j++) {
                    accs[j] = "{" + accs[j] + "}";
                }
            }
            Account[] acs = new Account[accs.length];
            for (int j = 0; j < accs.length; j++) {
                Account ac = (Account) XStreamFactory.get(Account.class, accs[j]);
                acs[j] = ac;
            }
            nf.setAccounts(acs);
        }

        index2 = SaveRet.indexOf("\"groups\":[");
        index3 = SaveRet.indexOf("],\"new_offset\":");
        if (index2 != -1 || index3 != -1) {
            String groups = SaveRet.substring(index2 + 10, index3);
            String[] grps = groups.split("\\}\\,\\{");
            if (grps.length > 1) {
                grps[0] = grps[0] + "}";
                grps[grps.length - 1] = "{" + grps[grps.length - 1];
                for (int j = 1; j < grps.length - 1; j++) {
                    grps[j] = "{" + grps[j] + "}";
                }
            }
            Group[] grs = new Group[grps.length];
            for (int j = 0; j < grps.length; j++) {
                Group gs = (Group) XStreamFactory.get(Group.class, grps[j]);
                grs[j] = gs;
            }
            nf.setGroups(grs);
        }
        
        //добавляю даты одыкватного формата
        for (int r = 0; r < nfslist.length; r++) {
            java.util.Date Ntime = new java.util.Date((long) Long.valueOf(nfslist[r].getDate()) * 1000);
            nfslist[r].setNdate(Ntime);
            if (nfslist[r].getCopy_post_date() != null) {
                Ntime = new java.util.Date((long) Long.valueOf(nfslist[r].getCopy_post_date()) * 1000);
                nfslist[r].setNdate(Ntime);
            }
        }
        //сохраняю массив новостей в возвращаемый объект
        nf.setNfslist(nfslist);
        return nf;
    }
}
